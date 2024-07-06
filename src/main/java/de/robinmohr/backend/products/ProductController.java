package de.robinmohr.backend.products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The ProductController class handles HTTP requests related to product management.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the Product object if found
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id)
                             .orElseThrow();
    }

    /**
     * Retrieves all products with pagination.
     *
     * @param pageable the pageable object specifying the page number and size
     * @return the page of Product objects
     */
    @GetMapping()
    public Page<Product> getAllProducts(Pageable pageable) {
        return productService.findAll(pageable);
    }

    /**
     * Updates a product in the system.
     *
     * @param id      the ID of the product to update
     * @param product the updated product information
     */
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable String id, @RequestBody Product product) {
        product.setProductId(id);
        productService.updateProduct(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id the ID of the product to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    /**
     * Creates a new product in the system.
     *
     * @param product the Product object representing the product to be created
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }
}
