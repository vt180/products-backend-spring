package de.robinmohr.backend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


/**
 * The ProductService interface represents a service for managing products in the system.
 */
public interface ProductService {

    /**
     * Creates a new product in the system.
     *
     * @param product the Product object representing the product to be created
     */
    void createProduct(Product product);

    /**
     * Updates a product in the system.
     *
     * @param product the updated product information
     */
    void updateProduct(Product product);

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     */
    void deleteProduct(String id);

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return an Optional containing the product if found, otherwise an empty Optional
     */
    Optional<Product> findById(String id);

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable the pageable object specifying the page number and size
     * @return the page of Product objects
     */
    Page<Product> findAll(Pageable pageable);
}
