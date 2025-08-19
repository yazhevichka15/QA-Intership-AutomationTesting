package tests;

import annotations.CleanUpDatabase;
import annotations.CleanUpKafkaTopics;
import models.MarketDataRecord;
import models.input.SelectionsStatuses;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import steps.*;
import utils.*;

import java.util.List;

import static utils.TestConstants.*;

@CleanUpDatabase
class ParameterizedIntegrationTests {

    private static final String TEST_EVENT_ID = RandomUtils.getRandomLongNumber(1, 100000).toString();

    private ParametrizedMarketsGenerator paramMarketsGenerator;
    private final KafkaSteps kafkaSteps = new KafkaSteps();
    private final DbSteps dbSteps = new DbSteps();
    private final AssertSteps assertSteps = new AssertSteps();

    @AfterEach
    void tearDown() {
        kafkaSteps.closeKafkaClients();
    }

    @ParameterizedTest
    @EnumSource(SelectionsStatuses.class)
    @CleanUpKafkaTopics({INPUT_TOPIC, OUTPUT_TOPIC})
    @DisplayName("Should process valid market event with fixed status {status}, verify database and output topic")
    void testProcessMarketEventWithFixedStatus(SelectionsStatuses status) {
        paramMarketsGenerator = new ParametrizedMarketsGenerator(TEST_EVENT_ID, status);

        String paramMarketEventJson = paramMarketsGenerator.getParametrizedMarketEventAsJson();
        String expectedParamProcessedMarketsJson = paramMarketsGenerator.getParametrizedProcessedMarketsAsJson();
        List<MarketDataRecord> expectedParamMarketData = paramMarketsGenerator.getParametrizedMarketDataRecords();

        kafkaSteps.sendMessageToTopic(INPUT_TOPIC, TEST_EVENT_ID, paramMarketEventJson);

        dbSteps.waitForDataInTable(expectedParamMarketData.size(), TEST_EVENT_ID);
        List<MarketDataRecord> marketDataTable = dbSteps.getMarketDataTable(TEST_EVENT_ID);

        assertSteps.assertMarketDataRecords(marketDataTable, expectedParamMarketData);

        List<ConsumerRecord<String, String>> actualRecords =
                kafkaSteps.consumeMessagesFromTopic(OUTPUT_TOPIC, TEST_EVENT_ID);
        assertSteps.assertKafkaRecord(TEST_EVENT_ID, expectedParamProcessedMarketsJson, actualRecords.getLast());
    }

    @ParameterizedTest
    @EnumSource(SelectionsStatuses.class)
    @CleanUpKafkaTopics({INPUT_TOPIC, OUTPUT_TOPIC})
    @DisplayName("Should process valid market report with fixed status {status}, verify database and output topic")
    void testProcessMarketReportWithFixedStatus(SelectionsStatuses status) {
        paramMarketsGenerator = new ParametrizedMarketsGenerator(TEST_EVENT_ID, status);

        String paramMarketReportJson = paramMarketsGenerator.getParametrizedMarketReportAsJson();
        String expectedParamProcessedReportMarketsJson = paramMarketsGenerator.getParametrizedProcessedReportMarketsAsJson();
        List<MarketDataRecord> expectedParamMarketReportData = paramMarketsGenerator.getParametrizedMarketReportDataRecords();

        kafkaSteps.sendMessageToTopic(INPUT_TOPIC, TEST_EVENT_ID, paramMarketReportJson);

        dbSteps.waitForDataInTable(expectedParamMarketReportData.size(), TEST_EVENT_ID);
        List<MarketDataRecord> marketDataTable = dbSteps.getMarketDataTable(TEST_EVENT_ID);

        assertSteps.assertMarketDataReportRecords(marketDataTable, expectedParamMarketReportData);

        List<ConsumerRecord<String, String>> actualRecords =
                kafkaSteps.consumeMessagesFromTopic(OUTPUT_TOPIC, TEST_EVENT_ID);
        assertSteps.assertKafkaRecord(TEST_EVENT_ID, expectedParamProcessedReportMarketsJson, actualRecords.getLast());
    }
}
