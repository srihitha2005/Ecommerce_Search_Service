package com.example.SearchServiceApplication.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListingDTO {
    private String productId;
    private String productName;
    private String description;
    private Integer rating;
    private String categoryName;
    private Long categoryId;
}