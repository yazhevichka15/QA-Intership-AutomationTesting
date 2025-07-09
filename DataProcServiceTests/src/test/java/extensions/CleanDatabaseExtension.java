package extensions;

import annotations.TestDependency;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.fail;

public class CleanDatabaseExtension implements BeforeEachCallback {
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getRequiredTestClass();

        Field[] annotatedFields = Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TestDependency.class))
                .toArray(Field[]::new);

        if (annotatedFields.length == 0) {
            fail("No fields annotated with @TestDependency found in test class");
        }

        for (Field field : annotatedFields) {
            field.setAccessible(true);

            if (!Jdbi.class.isAssignableFrom(field.getType())) {
                continue;
            }

            try {
                Jdbi jdbi = (Jdbi)field.get(null);

                if (jdbi == null) {
                    fail("Jdbi instance is null. Make sure it's initialized in @BeforeAll");
                }
                jdbi.useHandle(handle ->
                        handle.execute("TRUNCATE TABLE market_data RESTART IDENTITY")
                );
            } catch (Exception e) {
                fail("Failed to clear the market_data table. Error: " + e.getMessage());
            }
        }
    }
}
