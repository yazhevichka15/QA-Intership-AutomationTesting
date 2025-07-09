package tests;

import annotations.ExtendCleanDatabaseAndTopics;
import annotations.TestDependency;

import io.qameta.allure.Step;
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

@ExtendCleanDatabaseAndTopics
class MarketProcessingIntegrationTests {

    @TestDependency
    private static final String INPUT_TOPIC = "markets";

    @TestDependency
    private static final String OUTPUT_TOPIC = "processed_markets";

    @TestDependency
    private static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:29092";

    private static final String POSTGRES_JDBC_URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "11037";
    private static final String TEST_EVENT_ID = "12345";

    private KafkaProducer<String, String> producer;
    private KafkaConsumer<String, String> consumer;

    @TestDependency
    private static Jdbi jdbi;

    @BeforeAll
    @Step("JDBI initialisation")
    static void beforeAll() {
        jdbi = Jdbi.create(POSTGRES_JDBC_URL, POSTGRES_USER, POSTGRES_PASSWORD);
        jdbi.registerRowMapper(BeanMapper.factory(MarketDataRecord.class));
    }

    @BeforeEach
    @Step("Configuring Kafka clients")
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
    }

    @AfterEach
    @Step("Closing Kafka clients")
    void tearDown() {
        if (producer != null) producer.close();
        if (consumer != null) consumer.close();
    }

    @Test
    @DisplayName("Should process valid market event, verify database and output topic")
    void testProcessMarketEvent() throws ExecutionException, InterruptedException, TimeoutException {
        String testReportJson = createTestEventJson();
        ProducerRecord<String, String> sentRecord = new ProducerRecord<>(INPUT_TOPIC, TEST_EVENT_ID, testReportJson);

        producer.send(sentRecord).get(10, SECONDS);

        await().atMost(15, SECONDS)
                .pollInterval(500, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                    String selectQuery = "SELECT event_id, market_type_id, selection_type_id, price, probability, status FROM market_data WHERE event_id = :eventId::bigint";
                    List<MarketDataRecord> dbRecords = jdbi.withHandle(handle ->
                            handle.createQuery(selectQuery)
                                    .bind("eventId", TEST_EVENT_ID)
                                    .mapTo(MarketDataRecord.class)
                                    .list()
                    );

                    assertEquals(3, dbRecords.size(), "Expected 3 records in the database, but was found: " + dbRecords.size());

                    MarketDataRecord firstRecord = dbRecords.getFirst();
                    assertEquals(TEST_EVENT_ID, firstRecord.getEventId());
                    assertEquals(1L, firstRecord.getMarketTypeId());
                    assertEquals(2L, firstRecord.getSelectionTypeId());
                    assertEquals(2.15, firstRecord.getPrice());
                    assertEquals(0.5, firstRecord.getProbability());
                    assertEquals("active", firstRecord.getStatus());

                    MarketDataRecord secondRecord = dbRecords.get(1);
                    assertEquals(TEST_EVENT_ID, secondRecord.getEventId());
                    assertEquals(1L, secondRecord.getMarketTypeId());
                    assertEquals(1L, secondRecord.getSelectionTypeId());
                    assertEquals(4.64, secondRecord.getPrice());
                    assertEquals(1.75, secondRecord.getProbability());
                    assertEquals("suspended", secondRecord.getStatus());

                    MarketDataRecord thirdRecord = dbRecords.get(2);
                    assertEquals(TEST_EVENT_ID, thirdRecord.getEventId());
                    assertEquals(2L, thirdRecord.getMarketTypeId());
                    assertEquals(3L, thirdRecord.getSelectionTypeId());
                    assertEquals(0.0, thirdRecord.getPrice());
                    assertEquals(0.0, thirdRecord.getProbability());
                    assertEquals("win", thirdRecord.getStatus());
                });

        consumer.subscribe(Collections.singletonList(OUTPUT_TOPIC));

        ConsumerRecords<String, String> outputRecords = consumer.poll(Duration.ofSeconds(10));
        assertFalse(outputRecords.isEmpty(), "No messages received from the \"processed_markets\" topic in 10 second");

        List<ConsumerRecord<String, String>> actualMessages = new ArrayList<>();
        for (ConsumerRecord<String, String> outputRecord : outputRecords) {
            actualMessages.add(outputRecord);
        }
        actualMessages.removeIf(record ->
                !record.key().equals(TEST_EVENT_ID)
        );

        ConsumerRecord<String, String> receivedRecord = actualMessages.getLast();

        assertEquals(TEST_EVENT_ID, receivedRecord.key());
        assertEquals("""
                {"id":12345,"is_success":true,"unique_markets_ids":[1,2],"unique_selection_ids":[1,2,3]}""",
                receivedRecord.value()
        );
    }

    @Test
    @DisplayName("Should process valid market report, verify database and output topic")
    void testProcessMarketReport() throws ExecutionException, InterruptedException, TimeoutException {
        String testReportJson = createTestReportJson();
        ProducerRecord<String, String> sentRecord = new ProducerRecord<>(INPUT_TOPIC, TEST_EVENT_ID, testReportJson);

        producer.send(sentRecord).get(10, SECONDS);

        await().atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .untilAsserted(() -> {
                    String selectQuery = "SELECT event_id, market_type_id, selection_type_id, price, probability, status FROM market_data WHERE event_id = :eventId::bigint";
                    List<MarketDataRecord> dbRecords = jdbi.withHandle(handle ->
                        handle.createQuery(selectQuery)
                                .bind("eventId", TEST_EVENT_ID)
                                .mapTo(MarketDataRecord.class)
                                .list()
                    );

                    assertEquals(3, dbRecords.size(), "Expected 3 records in the database, but was found: " + dbRecords.size());

                    MarketDataRecord firstRecord = dbRecords.getFirst();
                    assertEquals(TEST_EVENT_ID, firstRecord.getEventId());
                    assertEquals(123L, firstRecord.getMarketTypeId());
                    assertEquals(20L, firstRecord.getSelectionTypeId());
                    assertEquals(21.5, firstRecord.getPrice());
                    assertEquals(2.445, firstRecord.getProbability());
                    assertEquals("active", firstRecord.getStatus());

                    MarketDataRecord secondRecord = dbRecords.get(1);
                    assertEquals(TEST_EVENT_ID, secondRecord.getEventId());
                    assertEquals(100L, secondRecord.getMarketTypeId());
                    assertEquals(15L, secondRecord.getSelectionTypeId());
                    assertEquals(17.5, secondRecord.getPrice());
                    assertEquals(2.055, secondRecord.getProbability());
                    assertEquals("suspended", secondRecord.getStatus());

                    MarketDataRecord thirdRecord = dbRecords.get(2);
                    assertEquals(TEST_EVENT_ID, thirdRecord.getEventId());
                    assertEquals(100L, thirdRecord.getMarketTypeId());
                    assertEquals(30L, thirdRecord.getSelectionTypeId());
                    assertEquals(31.5, thirdRecord.getPrice());
                    assertEquals(3.445, thirdRecord.getProbability());
                    assertEquals("win", thirdRecord.getStatus());
               });

        consumer.subscribe(Collections.singletonList(OUTPUT_TOPIC));

        ConsumerRecords<String, String> outputRecords = consumer.poll(Duration.ofSeconds(10));
        assertFalse(outputRecords.isEmpty(), "No messages received from the \"processed_markets\" topic in 10 second");

        List<ConsumerRecord<String, String>> actualMessages = new ArrayList<>();
        for (ConsumerRecord<String, String> outputRecord : outputRecords) {
            actualMessages.add(outputRecord);
        }
        actualMessages.removeIf(record ->
                !record.key().equals(TEST_EVENT_ID)
        );

        ConsumerRecord<String, String> receivedRecord = actualMessages.getLast();

        assertEquals(TEST_EVENT_ID, receivedRecord.key());
        assertEquals("""
                {"id":12345,"is_success":true,"processed_markets_ids":[123,100],"processed_selections_ids":[20,15,30]}""",
                receivedRecord.value());
    }

    @Test
    @DisplayName("Should process invalid input, verify database and output topic with an error message")
    void testProcessInvalidInput() throws ExecutionException, InterruptedException, TimeoutException {
        String testReportJson = createInvalidJson();
        ProducerRecord<String, String> sentRecord = new ProducerRecord<>(INPUT_TOPIC, TEST_EVENT_ID, testReportJson);

        producer.send(sentRecord).get(10, SECONDS);

        await().atMost(10, SECONDS)
                .pollInterval(1, SECONDS)
                .untilAsserted(() -> {
                    String selectQuery = "SELECT event_id FROM market_data WHERE event_id = :eventId::bigint";
                    List<Long> eventIds = jdbi.withHandle(handle ->
                            handle.createQuery(selectQuery)
                                    .bind("eventId", TEST_EVENT_ID)
                                    .mapTo(Long.class)
                                    .list()
                    );

                    assertTrue(eventIds.isEmpty(), "The table was expected to be empty, but records with event_id were found: " + eventIds.size());
                });

        consumer.subscribe(Collections.singletonList(OUTPUT_TOPIC));

        ConsumerRecords<String, String> outputRecords = consumer.poll(Duration.ofSeconds(10));
        assertFalse(outputRecords.isEmpty(), "No messages received from the \"processed_markets\" topic in 10 second");

        List<ConsumerRecord<String, String>> actualMessages = new ArrayList<>();
        for (ConsumerRecord<String, String> outputRecord : outputRecords) {
            actualMessages.add(outputRecord);
        }
        actualMessages.removeIf(record ->
                !record.key().equals(TEST_EVENT_ID)
        );

        ConsumerRecord<String, String> receivedRecord = actualMessages.getLast();

        assertEquals(TEST_EVENT_ID, receivedRecord.key());
        assertEquals("""
                {"id":-922337203685477580,"is_success":false,"error_description":"Deserialization error. Received message with wrong format."}""",
                receivedRecord.value()
        );
    }

    @Step("Create MarketEvent JSON")
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

    @Step("Create MarketReport JSON")
    private String createTestReportJson() {
        return """
                {
                    "id": 12345,
                    "message_type": "market_report",
                    "markets": [{
                        "market_type_id": 123,
                        "selections": [{
                            "selection_type_id": 20,
                            "status": "active"
                        }]
                    }, {
                        "market_type_id": 100,
                        "selections": [{
                            "selection_type_id": 15,
                            "status": "suspended"
                        }, {
                            "selection_type_id": 30,
                            "status": "win"
                        }]
                    }]
                }
                """;
    }

    @Step("Create invalid JSON")
    private String createInvalidJson() {
        return """
                {
                    "id": 12345,
                    "message_type": -012345,
                    "markets": []
                }
                """;
    }
}