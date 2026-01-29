package com.example.SearchServiceApplication.service;

import com.example.SearchServiceApplication.dto.ProductListingDTO;
import com.example.SearchServiceApplication.dto.SearchResponseDTO;
import java.util.List;

public interface SearchService {
    SearchResponseDTO search(String keyword, String sortBy, String direction, Long categoryId);
    List<ProductListingDTO> getSuggestions(String partialQuery);
    List<ProductListingDTO> getByCategory(Long categoryId);
}