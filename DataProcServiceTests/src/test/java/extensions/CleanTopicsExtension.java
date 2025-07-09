package extensions;

import annotations.TestDependency;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.TopicPartitionInfo;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.fail;

public class CleanTopicsExtension implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback {
    private AdminClient adminClient;
    private List<String> topicsToClean;

    private String KAFKA_BOOTSTRAP_SERVERS;

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        Class<?> testClass = context.getRequiredTestClass();

        Field[] annotatedFields = Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(TestDependency.class))
                .toArray(Field[]::new);

        if (annotatedFields.length == 0) {
            fail("No fields annotated with @TestDependency found in test class");
        }

        topicsToClean = new ArrayList<>();
        for (Field field : annotatedFields) {
            field.setAccessible(true);

            if (!String.class.isAssignableFrom(field.getType())) {
                continue;
            }

            try {
                String value = (String)field.get(null);

                if (field.getName().equals("KAFKA_BOOTSTRAP_SERVERS")) {
                    KAFKA_BOOTSTRAP_SERVERS = value;
                }
                else if (field.getName().equals("INPUT_TOPIC") || field.getName().equals("OUTPUT_TOPIC")) {
                    topicsToClean.add(value);
                }
            } catch (IllegalAccessException e) {
                fail("Failed to access field " + field.getName() + ". Error: " + e.getMessage());
            }
        }

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        adminClient = AdminClient.create(props);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        cleanTopics();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (adminClient != null) {
            adminClient.close();
        }
    }

    private void cleanTopics() {
        try {
            Map<TopicPartition, Long> endOffsets = getEndOffsets(topicsToClean);

            Map<TopicPartition, RecordsToDelete> recordsToDelete = new HashMap<>();
            endOffsets.forEach((partition, offset) -> {
                if (offset > 0) {
                    recordsToDelete.put(partition, RecordsToDelete.beforeOffset(offset));
                }
            });

            if (!recordsToDelete.isEmpty()) {
                adminClient.deleteRecords(recordsToDelete)
                        .all()
                        .get(10, TimeUnit.SECONDS);
                System.out.println("Successfully cleaned topics: " + topicsToClean);
            } else {
                System.out.println("No records to delete in topics: " + topicsToClean);
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("Failed to clean Kafka topics: " + topicsToClean, e);
        }
    }

    private Map<TopicPartition, Long> getEndOffsets(List<String> topics) throws ExecutionException, InterruptedException, TimeoutException {
        Map<TopicPartition, Long> endOffsets = new HashMap<>();

        for (String topic : topics) {
            TopicDescription description;
            try {
                description = adminClient.describeTopics(Collections.singleton(topic))
                        .topicNameValues()
                        .get(topic)
                        .get(10, TimeUnit.SECONDS);
            } catch (Exception e) {
                continue;
            }

            if (description != null) {
                for (TopicPartitionInfo partitionInfo : description.partitions()) {
                    TopicPartition partition = new TopicPartition(topic, partitionInfo.partition());

                    long offset = adminClient
                            .listOffsets(Collections.singletonMap(partition, new OffsetSpec.LatestSpec()))
                            .partitionResult(partition)
                            .get(10, TimeUnit.SECONDS)
                            .offset();
                    endOffsets.put(partition, offset);
                }
            }
        }
        return endOffsets;
    }
}