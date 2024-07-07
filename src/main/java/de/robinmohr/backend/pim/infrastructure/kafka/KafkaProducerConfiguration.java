package de.robinmohr.backend.pim.infrastructure.kafka;

import de.robinmohr.backend.pim.PimProduct;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;


/**
 * Configuration class for Kafka producer.
 * The class provides the necessary configuration for creating a Kafka producer using the Spring Kafka library.
 */
@Configuration
public class KafkaProducerConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Creates a Kafka producer factory with the provided configuration properties.
     * The method uses the Spring Kafka library to create a Kafka producer factory, which is responsible for creating instances of Kafka producers.
     * The factory uses the provided configuration properties to configure the producer instances.
     *
     * @return a Kafka producer factory with the specified configuration properties
     */
    @Bean
    public ProducerFactory<String, PimProduct> producerFactory() {
        final var configProps = new HashMap<String, Object>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Returns a KafkaTemplate for sending messages to Kafka.
     * This method creates a KafkaTemplate instance using the producerFactory() method from the KafkaProducerConfiguration class.
     * The KafkaTemplate is responsible for sending messages to Kafka topics.
     *
     * @return a KafkaTemplate for sending messages to Kafka
     */
    @Bean
    public KafkaTemplate<String, PimProduct> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
