package de.robinmohr.backend.product;

import ch.qos.logback.core.util.StringUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The DefaultProductSearchRepository class is an implementation of the ProductSearchRepository interface.
 * It provides methods to search for products based on category, sub-category, and title.
 */
@Repository
@RequiredArgsConstructor
public class ProductSearchRepositoryImpl
        implements ProductSearchRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * This method is a post-construct method that is called after the bean has been initialized.
     * It is responsible for creating the necessary indexes in the MongoDB collection for the Product class.
     * Specifically, it creates a text index on the productTitle field.
     */
    @PostConstruct
    public void createIndexes() {
        mongoTemplate.indexOps(Product.class)
                     .ensureIndex(new TextIndexDefinition.TextIndexDefinitionBuilder().onField("productTitle")
                                                                                      .build());
    }

    @Override
    public Page<Product> search(String category, String subCategory, String productTitle, Pageable pageable) {
        final var query = new Query();

        if (!StringUtil.isNullOrEmpty(category)) {
            query.addCriteria(Criteria.where("category")
                                      .is(category));
        }

        if (!StringUtil.isNullOrEmpty(subCategory)) {
            query.addCriteria(Criteria.where("subCategory")
                                      .is(subCategory));
        }

        if (!StringUtil.isNullOrEmpty(productTitle)) {
            query.addCriteria(Criteria.where("productTitle")
                                      .regex(productTitle));
        }
        final var totalQuery = Query.of(query);
        final var pagedQuery = query.with(pageable);

        List<Product> products = mongoTemplate.find(pagedQuery, Product.class);
        final var total = mongoTemplate.count(totalQuery, Product.class);

        return new PageImpl<>(products, pageable, total);
    }
}
