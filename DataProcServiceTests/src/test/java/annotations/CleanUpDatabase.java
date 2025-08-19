package annotations;

import extensions.CleanDatabaseExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({CleanDatabaseExtension.class})
public @interface CleanUpDatabase {
}
