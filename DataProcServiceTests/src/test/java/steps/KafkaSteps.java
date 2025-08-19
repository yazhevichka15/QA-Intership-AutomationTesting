package steps;

import static utils.TestConstants.*;

import io.qameta.allure.Step;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.StreamSupport;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class KafkaSteps {

    private final KafkaProducer<String, String> producer;
    private final KafkaConsumer<String, String> consumer;

    public KafkaSteps() {
        this.producer = createProducer();
        this.consumer = createConsumer();
    }

    private KafkaProducer<String, String> createProducer() {
        Properties producerProps = new Properties();
        producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new KafkaProducer<>(producerProps);
    }

    private KafkaConsumer<String, String> createConsumer() {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group-" + System.currentTimeMillis());
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return new KafkaConsumer<>(consumerProps);
    }

    @Step("Sending the message {message} to the topic {topic} with the key {key}")
    public void sendMessageToTopic(String topic, String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        try {
            producer.send(record).get(10, SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException("Failed to send the message to a topic " + topic, e);
        }
    }

    @Step("Get messages from the topic {topic} with the key {key}")
    public List<ConsumerRecord<String, String>> consumeMessagesFromTopic(String topic, String key) {
        consumer.subscribe(Collections.singletonList(topic));
        ConsumerRecords<String, String> outputRecords = consumer.poll(Duration.ofSeconds(10));
        assertFalse(outputRecords.isEmpty(), "No messages received from the \"" + topic + "\" topic in 10 second");

        List<ConsumerRecord<String, String>> actualMessages = StreamSupport
                .stream(outputRecords.records(topic).spliterator(), false)
                .filter(record -> record.key().equals(key))
                .toList();

        assertFalse(actualMessages.isEmpty(), "No messages with key " + key + " found");
        return actualMessages;
    }


    @Step("Close Kafka clients")
    public void closeKafkaClients() {
        if (producer != null) producer.close();
        if (consumer != null) consumer.close();
    }
}