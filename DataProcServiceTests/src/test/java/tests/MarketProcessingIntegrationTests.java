package tests;

import models.MarketDataRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jdbi.v3.core.Jdbi;

import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class MarketProcessingIntegrationTests {

    private static final String INPUT_TOPIC = "markets";
    private static final String OUTPUT_TOPIC = "processed_markets";
    private static final String TEST_EVENT_ID = "12345";

    private static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:29092";
    private static final String POSTGRES_JDBC_URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "11037";

    private KafkaProducer<String, String> producer;
    private KafkaConsumer<String, String> consumer;
    private static Jdbi jdbi;

    @BeforeAll
    static void beforeAll() {
        jdbi = Jdbi.create(POSTGRES_JDBC_URL, POSTGRES_USER, POSTGRES_PASSWORD);
        jdbi.registerRowMapper(BeanMapper.factory(MarketDataRecord.class));
    }

    @BeforeEach
    void setUp() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producer = new KafkaProducer<>(producerProps);

        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-" + System.currentTimeMillis());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<>(consumerProps);

        try {
            jdbi.useHandle(handle -> handle.execute("TRUNCATE TABLE market_data RESTART IDENTITY"));
        } catch (Exception e) {
            fail("Failed to clear the market_data table. Make sure that docker-compose is running and the \"app\" service has successfully applied the migrations.", e);
        }
    }

    @AfterEach
    void tearDown() {
        if (producer != null) producer.close();
        if (consumer != null) consumer.close();
    }

    @Test
    @DisplayName("Should process market event and verify database and output topic")
    void testProcessMarketEvent() throws ExecutionException, InterruptedException, TimeoutException {
        String testEventJson = createTestEventJson();

        producer.send(new ProducerRecord<>(INPUT_TOPIC, TEST_EVENT_ID, testEventJson)).get(10, SECONDS);

        await().atMost(15, SECONDS)
                .pollInterval(500, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    List<MarketDataRecord> records = jdbi.withHandle(handle ->
                            handle.createQuery("SELECT event_id, market_type_id, selection_type_id, price, probability, status FROM market_data WHERE event_id = :eventId::bigint")
                                    .bind("eventId", TEST_EVENT_ID)
                                    .mapTo(MarketDataRecord.class)
                                    .list()
                    );

                    assertEquals(3, records.size(), "Expected 3 records in the database, but was found: " + records.size());

                    MarketDataRecord firstRecord = records.getFirst();
                    assertEquals(TEST_EVENT_ID, firstRecord.getEventId());
                    assertEquals(1L, firstRecord.getMarketTypeId());
                    assertEquals(2L, firstRecord.getSelectionTypeId());
                    assertEquals(2.15, firstRecord.getPrice());
                    assertEquals(0.5, firstRecord.getProbability());
                    assertEquals("active", firstRecord.getStatus());

                    MarketDataRecord secondRecord = records.get(1);
                    assertEquals(TEST_EVENT_ID, secondRecord.getEventId());
                    assertEquals(1L, secondRecord.getMarketTypeId());
                    assertEquals(1L, secondRecord.getSelectionTypeId());
                    assertEquals(4.64, secondRecord.getPrice());
                    assertEquals(1.75, secondRecord.getProbability());
                    assertEquals("suspended", secondRecord.getStatus());

                    MarketDataRecord thirdRecord = records.get(2);
                    assertEquals(TEST_EVENT_ID, thirdRecord.getEventId());
                    assertEquals(2L, thirdRecord.getMarketTypeId());
                    assertEquals(3L, thirdRecord.getSelectionTypeId());
                    assertEquals(0.0, thirdRecord.getPrice());
                    assertEquals(0.0, thirdRecord.getProbability());
                    assertEquals("win", thirdRecord.getStatus());
                });

        consumer.subscribe(Collections.singletonList(OUTPUT_TOPIC));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
        List<ConsumerRecord<String, String>> actualMessages = new ArrayList<>();

        for (ConsumerRecord<String, String> record : records) {
            actualMessages.add(record);
        }
        actualMessages.removeIf(r -> !TEST_EVENT_ID.equals(r.key()));

        assertFalse(records.isEmpty(), "No messages received from the \"processed_markets\" topic in 10 second");

        ConsumerRecord<String, String> receivedRecord = actualMessages.getLast();

        assertEquals(TEST_EVENT_ID, receivedRecord.key());
        assertEquals("""
                {"id":12345,"is_success":true,"unique_markets_ids":[1,2],"unique_selection_ids":[1,2,3]}""",
                receivedRecord.value()
        );
    }


    private String createTestEventJson() {
        return """
                {
                   "id": 12345,
                   "message_type": "market_event",
                   "status": "active",
                   "markets": [{
                       "market_type_id": 1,
                       "specifiers": [],
                       "selections": [{
                           "selection_type_id": 2,
                           "status": 0,
                           "odds": {
                               "price": 2.15,
                               "probability": 0.5
                           }
                       }, {
                           "selection_type_id": 1,
                           "status": 1,
                           "odds": {
                               "price": 4.64,
                               "probability": 1.75
                           }
                       }]
                   }, {
                       "market_type_id": 2,
                       "specifiers": [],
                       "selections": [{
                           "selection_type_id": 3,
                           "status": 3,
                           "odds": {
                               "price": 1.22,
                               "probability": 0.01
                           }
                       }]
                   }]
                }
                """;
    }

    private String createTestReportJson() {
        return """
                
                """;
    }

    private String createInvalidJson() {
        return """
                
                """;
    }
}