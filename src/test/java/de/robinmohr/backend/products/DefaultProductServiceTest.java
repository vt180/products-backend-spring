package de.robinmohr.backend.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DefaultProductServiceTest {

    @Mock
    ProductRepository     productRepository;
    @InjectMocks
    DefaultProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test()
    void givenValidProduct_whenCreated_thenNoExceptions() {
        final var product = new Product.ProductBuilder().productId("38940")
                                                        .gender("boys")
                                                        .category("apparel")
                                                        .subCategory("bottomwear")
                                                        .productType("shorts")
                                                        .colour("blue")
                                                        .usage("casual")
                                                        .productTitle("Palm Tree Boys Blue Shorts")
                                                        .imageURL("http://assets.myntassets.com/v1/images/style/properties/423e296d366a31075609690410e80852_images.jpg")
                                                        .build();

        when(productRepository.save(product)).thenReturn(product);

        assertDoesNotThrow(() -> productService.createProduct(product));
        verify(productRepository).save(product);
    }

    @Test
    void givenValidProduct_whenUpdated_thenNoExceptions() {
        final var product = new Product.ProductBuilder().productId("38940")
                                                        .gender("boys")
                                                        .category("apparel")
                                                        .subCategory("bottomwear")
                                                        .productType("shorts")
                                                        .colour("blue")
                                                        .usage("casual")
                                                        .productTitle("Palm Tree Boys Blue Shorts")
                                                        .imageURL("http://assets.myntassets.com/v1/images/style/properties/423e296d366a31075609690410e80852_images.jpg")
                                                        .build();

        when(productRepository.save(product)).thenReturn(product);

        assertDoesNotThrow(() -> productService.updateProduct(product));
        verify(productRepository).save(product);
    }

    @Test
    void givenExistingId_whenDeleted_thenNoExceptions() {
        final var id = "38940";

        doNothing().when(productRepository)
                   .deleteById(id);

        assertDoesNotThrow(() -> productRepository.deleteById(id));
        verify(productRepository).deleteById(id);
    }

    @Test
    void givenExistingId_whenFind_thenReturnProduct() {
        final var id = "38940";
        final var product = new Product.ProductBuilder().productId("38940")
                                                        .gender("boys")
                                                        .category("apparel")
                                                        .subCategory("bottomwear")
                                                        .productType("shorts")
                                                        .colour("blue")
                                                        .usage("casual")
                                                        .productTitle("Palm Tree Boys Blue Shorts")
                                                        .imageURL("http://assets.myntassets.com/v1/images/style/properties/423e296d366a31075609690410e80852_images.jpg")
                                                        .build();

        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        final var foundProductOptional = productService.findById(id);
        verify(productRepository).findById(id);
        assertTrue(foundProductOptional.isPresent());
        assertEquals(product, foundProductOptional.get());
    }

    @Test
    void givenValidPageRequest_whenFindAll_thenReturnPage() {
        final var productOne = new Product.ProductBuilder().productId("38940")
                                                           .gender("boys")
                                                           .category("apparel")
                                                           .subCategory("bottomwear")
                                                           .productType("shorts")
                                                           .colour("blue")
                                                           .usage("casual")
                                                           .productTitle("Palm Tree Boys Blue Shorts")
                                                           .imageURL("http://assets.myntassets.com/v1/images/style/properties/423e296d366a31075609690410e80852_images.jpg")
                                                           .build();
        final var productTwo = new Product.ProductBuilder().productId("41988")
                                                           .gender("boys")
                                                           .category("apparel")
                                                           .subCategory("Topwear")
                                                           .productType("Shirts")
                                                           .colour("blue")
                                                           .usage("casual")
                                                           .productTitle("Gini and Jony Boys Check Blue Shirt")
                                                           .imageURL("http://assets.myntassets.com/v1/images/style/properties/d89f4ae894d44f192d64b42377d6c80b_images.jpg")
                                                           .build();
        final var page = PageRequest.of(1,
                                        2,
                                        Sort.by("productId")
                                            .ascending());

        final Page<Product> pageOfProducts = new PageImpl<>(List.of(productOne, productTwo));

        when(productRepository.findAll(page)).thenReturn(pageOfProducts);

        final var returnedPage = productService.findAll(page);
        verify(productRepository).findAll(page);
        assertEquals(pageOfProducts, returnedPage);
    }
}
