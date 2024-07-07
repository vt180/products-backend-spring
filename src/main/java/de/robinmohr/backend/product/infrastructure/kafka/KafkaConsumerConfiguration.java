package de.robinmohr.backend.product.infrastructure.kafka;

import de.robinmohr.backend.product.Product;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


/**
 * The KafkaConsumerConfiguration class is a configuration class that provides the necessary configuration for consuming messages from Kafka topics.
 * <p>
 * This class is annotated with @Configuration and @EnableKafka to enable the Kafka support in the Spring application context.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * The productKafkaListenerContainerFactory method creates a ConcurrentKafkaListenerContainerFactory
     * for consuming messages from Kafka topics with key of type String and value of type Product.
     *
     * @return The ConcurrentKafkaListenerContainerFactory for consuming messages from Kafka topics with key of type String and value of type Product.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Product> productKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Product> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(productConsumerFactory());
        return factory;
    }

    /**
     * Creates a ConsumerFactory for consuming messages from Kafka topics.
     *
     * @return the created ConsumerFactory
     */
    public ConsumerFactory<String, Product> productConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "de.robinmohr.*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Product.class));
    }
}
