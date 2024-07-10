package de.robinmohr.backend.product;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The ProductController class handles HTTP requests related to product management.
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173")
public class ProductController {

    private final ProductService productService;

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     *
     * @return the Product object if found
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id)
                             .orElseThrow();
    }

    /**
     * Finds products by category, sub-category, and title.
     *
     * @param productTitle the title of the product, can be null
     * @param category     the category of the product, can be null
     * @param subCategory  the sub-category of the product, can be null
     * @param page         the page number, default is 0
     * @param size         the page size, default is 10
     *
     * @return a Page of products matching the given category, sub-category, and title
     */
    @GetMapping
    public Page<Product> findAll(@RequestParam(required = false) String productTitle,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false) String subCategory,
                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                 @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return productService.findByCategoryAndSubCategoryAndTitle(category, subCategory, productTitle, PageRequest.of(page, size));
    }

    /**
     * Updates a product in the system.
     *
     * @param id      the ID of the product to update
     * @param product the updated product information
     */
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable String id, @Valid @RequestBody Product product) {
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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid @RequestBody Product product) {
        productService.createProduct(product);
    }
}
