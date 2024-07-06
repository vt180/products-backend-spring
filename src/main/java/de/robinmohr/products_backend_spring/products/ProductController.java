package de.robinmohr.products_backend_spring.products;

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


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.findById(id)
                             .orElseThrow();
    }

    @GetMapping()
    public Page<Product> getAllProducts(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable String id, @RequestBody Product product) {
        product.setProductId(id);
        productService.updateProduct(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }
}
