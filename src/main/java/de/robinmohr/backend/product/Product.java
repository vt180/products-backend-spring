package de.robinmohr.backend.product;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    private String productId;
    private String gender;
    @NotBlank
    private String category;
    @NotBlank
    private String subCategory;
    private String productType;
    private String colour;
    private String usage;
    @TextIndexed(weight = 3)
    @NotBlank
    private String productTitle;
    @Pattern(regexp = "^(http[s]?:\\/\\/(www\\.)?)[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,5}(:[0-9]{1,5})?(\\/.*)?$")
    private String imageURL;
}
