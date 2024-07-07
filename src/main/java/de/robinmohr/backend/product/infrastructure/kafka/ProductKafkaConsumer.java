package de.robinmohr.backend.product.infrastructure.kafka;

import de.robinmohr.backend.product.Product;
import de.robinmohr.backend.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * The ProductKafkaConsumer class is responsible for consuming product messages from a Kafka topic.
 * It listens to the specified topic and group, and creates or updates the product in the system.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProductKafkaConsumer {

    private final ProductService productService;

    /**
     * This method is responsible for consuming a product message from a Kafka topic.
     * It listens to the specified topic and group, and creates or updates the product in the system.
     *
     * @param product the Product object representing the product to be consumed and processed
     */
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.groupId}", concurrency = "1", containerFactory = "productKafkaListenerContainerFactory")
    void consumeProduct(@Valid Product product) {
        log.info("Consuming product message [{}]", product);

        //normally we would do a search by product identifier (e.g. GTIN) and create / update accordingly, but this is a simple example with valid IDs, so we just save.
        productService.createProduct(product);
    }
}
