package de.robinmohr.backend.product.infrastructure.kafka;

import de.robinmohr.backend.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * The ProductKafkaProducer class is responsible for sending products to a Kafka topic.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProductKafkaProducer {

    private final KafkaTemplate<String, Product> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    /**
     * Sends a product to a Kafka topic using a Kafka producer.
     *
     * @param product the product to be sent
     */
    public void sendMessage(Product product) {
        final var future = kafkaTemplate.send(topicName, product);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}] and partition=[{}]", product,
                         result.getRecordMetadata()
                               .offset(), result.getRecordMetadata().partition());
            } else {
                log.warn("Unable to send message=[{}] due to : {}", product, ex.getMessage());
            }
        });
    }
}
