package steps;

import extensions.CleanDatabaseExtension;
import models.MarketDataRecord;

import io.qameta.allure.Step;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static java.util.concurrent.TimeUnit.SECONDS;

public class DbSteps {

    private final Jdbi jdbi;

    public DbSteps() {
        this.jdbi = CleanDatabaseExtension.getJdbi();
    }

    @Step("Waiting for {expectedSize} records in the table with event_id {eventId}")
    public void waitForDataInTable(int expectedSize, String eventId) {
        await().atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .untilAsserted(() -> {
                    List<MarketDataRecord> dbRecords = getMarketDataTable(eventId);
                    assertEquals(expectedSize, dbRecords.size(),
                            "Database records size mismatch. Expected: " + expectedSize + ", but found: " + dbRecords.size());
                });
    }

    @Step("Get records from the market_data table with event_id {eventId}")
    public List<MarketDataRecord> getMarketDataTable(String eventId) {
        String selectQuery =
                "SELECT event_id, market_type_id, selection_type_id, price, probability, status FROM market_data WHERE event_id = :eventId::bigint";
        return jdbi.withHandle(handle ->
                handle.createQuery(selectQuery)
                        .bind("eventId", eventId)
                        .mapTo(MarketDataRecord.class)
                        .list()
        );
    }

    @Step("Checking the market_data table is empty with event_id {eventId} records")
    public void assertTableIsEmpty(String eventId) {
        await().atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .untilAsserted(() -> {
                    String selectQuery = "SELECT event_id FROM market_data WHERE event_id = :eventId::bigint";
                    List<Long> eventIds = jdbi.withHandle(handle ->
                            handle.createQuery(selectQuery)
                                    .bind("eventId", eventId)
                                    .mapTo(Long.class)
                                    .list()
                    );
                    assertTrue(eventIds.isEmpty(),
                            "The table was expected to be empty, but records were found: " + eventIds.size());
                });
    }
}