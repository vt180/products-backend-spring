package de.robinmohr.backend;

import de.robinmohr.backend.product.Product;
import de.robinmohr.backend.product.infrastructure.kafka.ProductKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


/**
 * The BootStrapper class is responsible for loading products from a CSV file and publishing them using a Kafka producer.
 * It contains a method that acts as a CommandLineRunner to perform the loading and publishing of products.
 */
@Configuration
@Slf4j
public class BootStrapper {

    /**
     * Loads products from a CSV file and publishes them using a Kafka producer.
     *
     * @param kafkaProducer the Kafka producer used to publish the products
     *
     * @return a CommandLineRunner that loads and publishes the products
     */
    @Bean
    public CommandLineRunner productLoader(ProductKafkaProducer kafkaProducer) {
        return args -> {
            log.info("Application started and initializing!");

            try (final var fileReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                                                                                                                  .getResourceAsStream("fashion.csv"))))) {
                fileReader.lines()
                          .map(BootStrapper::createProductFromCsvLine)
                          .forEach(product -> {
                              log.info("publishing product: {}", product);
                              kafkaProducer.sendMessage(product);
                              log.info("published product: {}", product);
                          });
            } catch (IOException exception) {
                log.error("Error occurred while reading CSV file:", exception);
            }
            log.info("Initialization complete!");
        };
    }

    private static Product createProductFromCsvLine(final String csvLine) {
        final var lineAsArray = csvLine.split(",");
        return Product.builder()
                      .productId(lineAsArray[0])
                      .productTitle(lineAsArray[7])
                      .productType(lineAsArray[4])
                      .usage(lineAsArray[6])
                      .colour(lineAsArray[5])
                      .gender(lineAsArray[1])
                      .imageURL(lineAsArray[9])
                      .subCategory(lineAsArray[3])
                      .category(lineAsArray[2])
                      .build();
    }
}
