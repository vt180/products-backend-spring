package de.robinmohr.products_backend_spring.products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static de.robinmohr.products_backend_spring.util.JsonUtil.json;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void givenExistingId_whenFind_thenReturnProduct() throws Exception {
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

        when(productService.findById(id)).thenReturn(Optional.of(product));

        mockMvc.perform(get("/api/v1/products/" + id))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))
               .andExpect(jsonPath("$.productId").value("38940"))
               .andExpect(jsonPath("$.gender").value("boys"))
               .andExpect(jsonPath("$.category").value("apparel"))
               .andExpect(jsonPath("$.subCategory").value("bottomwear"))
               .andExpect(jsonPath("$.productType").value("shorts"))
               .andExpect(jsonPath("$.colour").value("blue"))
               .andExpect(jsonPath("$.usage").value("casual"))
               .andExpect(jsonPath("$.productTitle").value("Palm Tree Boys Blue Shorts"))
               .andExpect(jsonPath("$.imageURL").value("http://assets.myntassets.com/v1/images/style/properties/423e296d366a31075609690410e80852_images.jpg"));

        verify(productService).findById(id);
    }

    @Test
    void givenValidPageRequest_whenFindAll_thenReturnPage() throws Exception {
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

        when(productService.findAll(page)).thenReturn(pageOfProducts);

        mockMvc.perform(get("/api/v1/products?page=1&size=2&sort=productId,asc"))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(APPLICATION_JSON))
               .andExpect(jsonPath("$.content").isArray())
               .andExpect(jsonPath("$.content").isNotEmpty())
               .andExpect(jsonPath("$.content.length()").value(2))
               .andExpect(jsonPath("$.content[0].productId").value("38940"))
               .andExpect(jsonPath("$.content[0].gender").value("boys"))
               .andExpect(jsonPath("$.content[0].category").value("apparel"))
               .andExpect(jsonPath("$.content[0].subCategory").value("bottomwear"))
               .andExpect(jsonPath("$.content[0].productType").value("shorts"))
               .andExpect(jsonPath("$.content[0].colour").value("blue"))
               .andExpect(jsonPath("$.content[0].usage").value("casual"))
               .andExpect(jsonPath("$.content[0].productTitle").value("Palm Tree Boys Blue Shorts"))
               .andExpect(jsonPath("$.content[0].imageURL").value("http://assets.myntassets.com/v1/images/style/properties/423e296d366a31075609690410e80852_images.jpg"))
               .andExpect(jsonPath("$.content[1].productId").value("41988"))
               .andExpect(jsonPath("$.content[1].gender").value("boys"))
               .andExpect(jsonPath("$.content[1].category").value("apparel"))
               .andExpect(jsonPath("$.content[1].subCategory").value("Topwear"))
               .andExpect(jsonPath("$.content[1].productType").value("Shirts"))
               .andExpect(jsonPath("$.content[1].colour").value("blue"))
               .andExpect(jsonPath("$.content[1].usage").value("casual"))
               .andExpect(jsonPath("$.content[1].productTitle").value("Gini and Jony Boys Check Blue Shirt"))
               .andExpect(jsonPath("$.content[1].imageURL").value("http://assets.myntassets.com/v1/images/style/properties/d89f4ae894d44f192d64b42377d6c80b_images.jpg"));
    }

    @Test
    void givenValidProduct_whenUpdated_thenNoExceptions() throws Exception {
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

        doNothing().when(productService)
                   .updateProduct(any(Product.class));

        mockMvc.perform(put("/api/v1/products/" + id).contentType(APPLICATION_JSON)
                                                     .content(json(product)))
               .andExpect(status().isOk());

        verify(productService).updateProduct(any(Product.class));
    }

    @Test
    void givenExistingId_whenDeleted_thenNoExceptions() throws Exception {
        final var id = "38940";

        doNothing().when(productService)
                   .deleteProduct(id);

        mockMvc.perform(delete("/api/v1/products/" + id))
               .andDo(print())
               .andExpect(status().isNoContent());

        verify(productService).deleteProduct(id);
    }

    @Test
    void givenValidProduct_whenCreated_thenNoExceptions() throws Exception {
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
        doNothing().when(productService)
                   .updateProduct(any(Product.class));

        mockMvc.perform(post("/api/v1/products").contentType(APPLICATION_JSON)
                                                .content(json(product)))
               .andDo(print())
               .andExpect(status().isCreated());
    }
}
