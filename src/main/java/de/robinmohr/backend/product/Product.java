package de.robinmohr.backend.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;


/**
 * The Product class represents a product in the system.
 * It contains information about the product's ID and other attributes.
 */
@Data
@AllArgsConstructor
@Builder
public class Product {

    /**
     * The Product class represents a product in the system.
     * It contains information about the product's ID and other attributes.
     *
     * @param productId the id to be set
     */
    public Product(String productId) {
        this.productId = productId;
    }

    @Id
    private String productId;
    private String gender;
    private String category;
    private String subCategory;
    private String productType;
    private String colour;
    private String usage;
    @TextIndexed(weight = 3)
    private String productTitle;
    private String imageURL;
}
