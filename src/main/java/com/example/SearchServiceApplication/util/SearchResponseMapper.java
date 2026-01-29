package com.example.SearchServiceApplication.util;

import com.example.SearchServiceApplication.document.ProductSearchDocument;
import com.example.SearchServiceApplication.dto.ProductListingDTO;

public class SearchResponseMapper {

    private SearchResponseMapper() {}

    public static ProductListingDTO toDTO(ProductSearchDocument doc) {
        ProductListingDTO dto = new ProductListingDTO();

        dto.setProductId(doc.getProductId());
        dto.setProductName(doc.getProductName());
        dto.setDescription(doc.getDescription());
        dto.setRating(doc.getRating());

        if (doc.getCategory() != null) {
            dto.setCategoryName(doc.getCategory().getName());
            dto.setCategoryId(doc.getCategory().getCategoryId());
        }

        return dto;
    }
}