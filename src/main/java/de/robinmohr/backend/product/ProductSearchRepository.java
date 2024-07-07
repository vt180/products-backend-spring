package de.robinmohr.backend.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * The ProductSearchRepository interface is used for searching products in the system.
 * It provides a method to find products by category, sub-category, and title.
 */
public interface ProductSearchRepository {

    /**
     * Finds products by category, sub-category, and title.
     *
     * @param category      the category of the product
     * @param subCategory   the sub-category of the product
     * @param productTitle  the title of the product
     * @param pageable      the pageable object for pagination
     * @return a page of products matching the given category, sub-category, and title
     */
    Page<Product> search(String category, String subCategory, String productTitle, Pageable pageable);
}
