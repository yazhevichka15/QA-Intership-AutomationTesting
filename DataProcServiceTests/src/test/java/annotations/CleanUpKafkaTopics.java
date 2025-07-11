package annotations;

import extensions.CleanTopicsExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith({CleanTopicsExtension.class})
public @interface CleanUpKafkaTopics {
    String[] value();
}
