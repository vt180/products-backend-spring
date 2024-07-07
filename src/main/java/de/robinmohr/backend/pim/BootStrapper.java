package de.robinmohr.backend.pim;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;


/**
 * The BootStrapper class is responsible for initializing the application by loading products from a CSV file
 * and publishing  them with the PimProductService.
 * This simulates some product information system publishing new product information.
 */
@Configuration
@Slf4j
public class BootStrapper {

    /**
     * Loads products from a CSV file and saves them to the ProductService.
     *
     * @param productService the ProductService object to use for saving the products
     *
     * @return a CommandLineRunner object that loads and saves the products
     */
    @Bean
    public CommandLineRunner productLoader(PimProductService productService) {
        return args -> {
            log.info("Application started and initializing!");

            try (final var fileReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader()
                                                                                                                  .getResourceAsStream("fashion.csv"))))) {
                fileReader.lines()
                          .limit(55) //TODO: for now only first 55 products
                          .map(BootStrapper::createProductFromCsvLine)
                          .forEach(product -> {
                              log.info("publishing product: {}", product);
                              productService.publish(product);
                              log.info("published product: {}", product);
                          });
            } catch (IOException exception) {
                log.error("Error occurred while reading CSV file:", exception);
            }
            log.info("Initialization complete!");
        };
    }

    private static PimProduct createProductFromCsvLine(final String csvLine) {
        final var lineAsArray = csvLine.split(",");
        return new PimProduct(lineAsArray[0], lineAsArray[1], lineAsArray[2], lineAsArray[3], lineAsArray[4], lineAsArray[5], lineAsArray[6], lineAsArray[7], lineAsArray[9]);
    }
}
