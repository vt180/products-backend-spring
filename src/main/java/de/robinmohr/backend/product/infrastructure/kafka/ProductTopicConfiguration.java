package de.robinmohr.backend.product.infrastructure.kafka;


import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;


/**
 * This class is responsible for configuring the Kafka topic related to the product search.
 * It uses the values from the application.properties file to set up the topic.
 */
@Configuration
public class ProductTopicConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.topic.name}")
    private String topicName;

    /**
     * Configures the Kafka admin for the product search topic.
     *
     * @return The configured KafkaAdmin object.
     */
    @Bean
    public KafkaAdmin kafkaAdminConfiguration() {
        final Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    /**
     * Configures and returns the Kafka topic for product search.
     *
     * @return The Kafka topic for product search.
     */
    @Bean
    public NewTopic productSearchTopic() {
        return TopicBuilder.name(topicName)
                           .partitions(1)
                           .replicas(1)
                           .build();
    }
}
