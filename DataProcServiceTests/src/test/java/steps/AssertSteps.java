package steps;

import models.MarketDataEntity;
import models.MarketDataRecord;
import models.input.SelectionsStatuses;
import utils.JsonTestUtils;

import io.qameta.allure.Step;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssertSteps {
    @Step("Checking expected {expectedRecords} and received {actualRecords} database records for MarketEvent")
    public void assertMarketDataRecords(List<MarketDataRecord> actualRecords, List<MarketDataEntity> expectedRecords) {
        actualRecords.forEach(actual -> {
            MarketDataEntity expected = expectedRecords.stream()
                    .filter(e ->
                            e.getMarketTypeId().equals(actual.getMarketTypeId()) &&
                            e.getSelectionTypeId().equals(actual.getSelectionTypeId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new AssertionError("Expected record not found"));

            assertEquals(expected.getEventId().toString(), actual.getEventId());
            assertEquals(expected.getMarketTypeId(), actual.getMarketTypeId());
            assertEquals(expected.getSelectionTypeId(), actual.getSelectionTypeId());
            assertEquals(expected.getStatus(), actual.getStatus());

            if (SelectionsStatuses.fromString(actual.getStatus()).isFinal()) {
                assertEquals(0.0, actual.getPrice(), 0.001);
                assertEquals(0.0, actual.getProbability(), 0.001);
            } else {
                assertEquals(expected.getPrice(), actual.getPrice(), 0.001);
                assertEquals(expected.getProbability(), actual.getProbability(), 0.001);
            }
        });
    }

    @Step("Checking expected {expectedRecords} and received {actualRecords} database records for MarketReport")
    public void assertMarketDataReportRecords(List<MarketDataRecord> actualRecords, List<MarketDataEntity> expectedRecords) {
        actualRecords.forEach(actual -> {
            MarketDataEntity expected = expectedRecords.stream()
                    .filter(e ->
                            e.getMarketTypeId().equals(actual.getMarketTypeId()) &&
                            e.getSelectionTypeId().equals(actual.getSelectionTypeId())
                    )
                    .findFirst()
                    .orElseThrow(() -> new AssertionError(
                            "Expected record not found"));

            assertEquals(expected.getEventId().toString(), actual.getEventId());
            assertEquals(expected.getMarketTypeId(), actual.getMarketTypeId());
            assertEquals(expected.getSelectionTypeId(), actual.getSelectionTypeId());
            assertEquals(expected.getStatus(), actual.getStatus());
            assertEquals(expected.getPrice(), actual.getPrice(), 0.001);
            assertEquals(expected.getProbability(), actual.getProbability(), 0.001);
        });
    }

    @Step("Checking expected {expectedJson} and received {actualRecord} Kafka messages with key {key}")
    public void assertKafkaRecord(String key, String expectedJson, ConsumerRecord<String, String> actualRecord) {
        assertEquals(key, actualRecord.key(),
                "Message key mismatch");
        JsonTestUtils.assertJsonEquals(actualRecord.value(), expectedJson);
    }
}