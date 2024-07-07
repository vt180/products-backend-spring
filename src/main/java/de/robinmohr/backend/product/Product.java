package de.robinmohr.backend.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * The Product class represents a product in the system.
 * It contains information about the product's ID and other attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = Product.COLLECTION)
public class Product {

    /**
     * The COLLECTION variable represents the collection name in the database where the products are stored.
     */
    public static final String COLLECTION = "products";

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
