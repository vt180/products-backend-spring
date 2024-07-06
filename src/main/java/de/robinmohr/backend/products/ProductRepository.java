package de.robinmohr.backend.products;

import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * ProductRepository is an interface that extends the MongoRepository interface.
 * It provides CRUD operations for managing Product objects in the system.
 */
public interface ProductRepository
        extends MongoRepository<Product, String> {}
