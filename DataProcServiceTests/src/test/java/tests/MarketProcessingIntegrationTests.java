package tests;

import annotations.CleanUpDatabase;
import annotations.CleanUpKafkaTopics;

import steps.*;
import utils.*;

import models.MarketDataRecord;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.*;

import java.util.List;

import static utils.TestConstants.*;

@CleanUpDatabase
class MarketProcessingIntegrationTests {

    private static final String TEST_EVENT_ID = RandomUtils.getRandomLongNumber(1, 100000).toString();

    private final MarketsGenerator marketsGenerator = new MarketsGenerator(Long.parseLong(TEST_EVENT_ID));
    private final KafkaSteps kafkaSteps = new KafkaSteps();
    private final DbSteps dbSteps = new DbSteps();
    private final AssertSteps assertSteps = new AssertSteps();

    @AfterEach
    void tearDown() {
        kafkaSteps.closeKafkaClients();
    }

    @Test
    @CleanUpKafkaTopics({INPUT_TOPIC, OUTPUT_TOPIC})
    @DisplayName("Should process valid market event, verify database and output topic")
    void testProcessMarketEvent() {
        String marketEventJson = marketsGenerator.getMarketEventAsJson();
        String expectedProcessedMarketsJson = marketsGenerator.getProcessedMarketsAsJson();
        List<MarketDataRecord> expectedMarketData = marketsGenerator.getMarketDataRecords();

        kafkaSteps.sendMessageToTopic(INPUT_TOPIC, TEST_EVENT_ID, marketEventJson);

        dbSteps.waitForDataInTable(expectedMarketData.size(), TEST_EVENT_ID);
        List<MarketDataRecord> marketDataTable = dbSteps.getMarketDataTable(TEST_EVENT_ID);

        assertSteps.assertMarketDataRecords(marketDataTable, expectedMarketData);

        List<ConsumerRecord<String, String>> actualRecords =
                kafkaSteps.consumeMessagesFromTopic(OUTPUT_TOPIC, TEST_EVENT_ID);
        assertSteps.assertKafkaRecord(TEST_EVENT_ID, expectedProcessedMarketsJson, actualRecords.getLast());
    }

    @Test
    @CleanUpKafkaTopics({INPUT_TOPIC, OUTPUT_TOPIC})
    @DisplayName("Should process valid market report, verify database and output topic")
    void testProcessMarketReport() {
        String marketReportJson = marketsGenerator.getMarketReportAsJson();
        String expectedProcessedReportMarketsJson = marketsGenerator.getProcessedReportMarketsAsJson();
        List<MarketDataRecord> expectedMarketData = marketsGenerator.getMarketDataReportRecords();

        kafkaSteps.sendMessageToTopic(INPUT_TOPIC, TEST_EVENT_ID, marketReportJson);

        dbSteps.waitForDataInTable(expectedMarketData.size(), TEST_EVENT_ID);
        List<MarketDataRecord> marketDataTable = dbSteps.getMarketDataTable(TEST_EVENT_ID);

        assertSteps.assertMarketDataReportRecords(marketDataTable, expectedMarketData);

        List<ConsumerRecord<String, String>> actualRecords =
                kafkaSteps.consumeMessagesFromTopic(OUTPUT_TOPIC, TEST_EVENT_ID);
        assertSteps.assertKafkaRecord(TEST_EVENT_ID, expectedProcessedReportMarketsJson, actualRecords.getLast());
    }

    @Test
    @CleanUpKafkaTopics({INPUT_TOPIC, OUTPUT_TOPIC})
    @DisplayName("Should process invalid input, verify database and output topic with an error message")
    void testProcessInvalidInput() {
        String invalidJson = marketsGenerator.getInvalidJson();
        String expectedErrorMessageJson = marketsGenerator.getErrorMessageAsJson();

        kafkaSteps.sendMessageToTopic(INPUT_TOPIC, TEST_EVENT_ID, invalidJson);

        dbSteps.assertTableIsEmpty(TEST_EVENT_ID);

        List<ConsumerRecord<String, String>> actualRecords =
                kafkaSteps.consumeMessagesFromTopic(OUTPUT_TOPIC, TEST_EVENT_ID);
        assertSteps.assertKafkaRecord(TEST_EVENT_ID, expectedErrorMessageJson, actualRecords.getLast());
    }
}