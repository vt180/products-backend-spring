package de.robinmohr.backend.pim.infrastructure.kafka;

import de.robinmohr.backend.pim.PimProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * The ProductKafkaProducer class is responsible for sending pim products to a Kafka topic.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PimProductKafkaProducer {

    private final KafkaTemplate<String, PimProduct> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    /**
     * Sends a pim product to a Kafka topic using a Kafka producer.
     *
     * @param pimProduct the pim product to be sent
     */
    public void sendMessage(PimProduct pimProduct) {
        final var future = kafkaTemplate.send(topicName, pimProduct);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]",
                         pimProduct,
                         result.getRecordMetadata()
                               .offset());
            } else {
                log.warn("Unable to send message=[{}] due to : {}", pimProduct, ex.getMessage());
            }
        });
    }
}
