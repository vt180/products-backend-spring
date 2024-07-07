package de.robinmohr.backend.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * The DefaultProductService class is an implementation of the ProductService interface.
 * It provides methods for creating, updating, deleting, finding products, and retrieving all products with pagination.
 */
@Service
@RequiredArgsConstructor
public class DefaultProductService
        implements ProductService {

    private final ProductRepository productRepository;


    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByCategoryAndSubCategoryAndTitle(String category, String subCategory, String productTitle, Pageable pageable) {
        return productRepository.search(category, subCategory, productTitle, pageable);
    }
}
