package de.robinmohr.products_backend_spring.products;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProductRepository
        extends MongoRepository<Product, String> {}
