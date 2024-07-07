package de.robinmohr.backend.product.infrastructure.kafka;

import de.robinmohr.backend.product.Product;
import de.robinmohr.backend.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class ProductEventConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.groupId}", concurrency = "1")
    void consumeProductEvent(Product product) {
        log.info("Consuming product [{}]", product);

        //normally we would do a search by product identifier (e.g. GTIN) and create / update accordingly, but this is a simple example with valid IDs, so we just save.
        productService.createProduct(product);
    }
}
