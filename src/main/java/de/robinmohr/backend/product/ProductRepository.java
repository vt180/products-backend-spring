package de.robinmohr.backend.product;

import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * The ProductRepository interface represents a repository for managing products in the system.
 * It extends the MongoRepository interface and the ProductSearchRepository interface.
 */
public interface ProductRepository
        extends MongoRepository<Product, String>, ProductSearchRepository {}
