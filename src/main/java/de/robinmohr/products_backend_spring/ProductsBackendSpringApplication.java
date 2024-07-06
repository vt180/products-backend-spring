package de.robinmohr.products_backend_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The ProductsBackendSpringApplication class is the entry point for running the application.
 * It is a Spring Boot application that starts the Spring context and runs the application.
 */
@SpringBootApplication
public class ProductsBackendSpringApplication {

	/**
	 * The main method is the entry point for running the application.
	 * It starts the Spring context and runs the application.
	 *
	 * @param args the command-line arguments passed to the main method
	 */
	public static void main(String[] args) {
		SpringApplication.run(ProductsBackendSpringApplication.class, args);
	}

}
