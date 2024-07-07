package de.robinmohr.backend.pim;

public record PimProduct(
        String productId,
        String gender,
        String category,
        String subCategory,
        String productType,
        String colour,
        String usage,
        String productTitle,
        String imageURL
) {}
