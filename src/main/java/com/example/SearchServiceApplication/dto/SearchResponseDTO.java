package com.example.SearchServiceApplication.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponseDTO {

    private String query;
    private Integer totalResults;
    private String message;
    private List<ProductListingDTO> results;
}


