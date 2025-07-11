package extensions;

import models.MarketDataRecord;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static org.junit.jupiter.api.Assertions.fail;

public class CleanDatabaseExtension implements BeforeAllCallback, BeforeEachCallback {

    private static final String POSTGRES_JDBC_URL = "jdbc:postgresql://localhost:5432/mydatabase";
    private static final String POSTGRES_USER = "postgres";
    private static final String POSTGRES_PASSWORD = "11037";
    private static Jdbi jdbi;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        jdbi = Jdbi.create(POSTGRES_JDBC_URL, POSTGRES_USER, POSTGRES_PASSWORD);
        jdbi.registerRowMapper(BeanMapper.factory(MarketDataRecord.class));
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        try {
            jdbi.useHandle(handle ->
                    handle.execute("TRUNCATE TABLE market_data RESTART IDENTITY")
            );
            System.out.println("Successfully cleaned database \"mydatabase\"");
        } catch (Exception e) {
            fail("Failed to clear the market_data table. Error: " + e.getMessage());
        }
    }
}
