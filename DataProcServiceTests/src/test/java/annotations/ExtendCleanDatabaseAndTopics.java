package annotations;

import extensions.CleanDatabaseExtension;
import extensions.CleanTopicsExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({CleanDatabaseExtension.class, CleanTopicsExtension.class})
public @interface ExtendCleanDatabaseAndTopics {
}
