package de.robinmohr.backend;

import org.springframework.boot.SpringApplication;

public class TestProductsBackendSpringApplication {

	public static void main(String[] args) {
		SpringApplication.from(ProductsBackendSpringApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
