package steps;

import io.qameta.allure.Step;
import models.MarketDataRecord;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestConstants.*;

public class DbSteps {
    private static final Jdbi jdbi;

    static {
        jdbi = Jdbi.create(POSTGRES_JDBC_URL, POSTGRES_USER, POSTGRES_PASSWORD);
        jdbi.registerRowMapper(BeanMapper.factory(MarketDataRecord.class));
    }

    @Step("Waiting for {expectedSize} records in the table for event_id {eventId}")
    public void waitForDataInTable(int expectedSize, String eventId) {
        await().atMost(15, SECONDS)
                .pollInterval(1, SECONDS)
                .untilAsserted(() -> {
                    List<MarketDataRecord> dbRecords = getMarketDataTable(eventId);
                    assertEquals(expectedSize, dbRecords.size(),
                            "Database records size mismatch. Expected: " + expectedSize + ", but found: " + dbRecords.size());
                });
    }

    @Step("Get records from the market_data table for event_id {eventId}")
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
}