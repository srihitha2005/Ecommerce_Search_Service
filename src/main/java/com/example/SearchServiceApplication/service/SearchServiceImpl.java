package com.example.SearchServiceApplication.service;

import com.example.SearchServiceApplication.document.ProductSearchDocument;
import com.example.SearchServiceApplication.dto.ProductListingDTO;
import com.example.SearchServiceApplication.dto.SearchResponseDTO;
import com.example.SearchServiceApplication.repository.ProductSearchRepository;
import com.example.SearchServiceApplication.util.SearchResponseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ProductSearchRepository productSearchRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public SearchResponseDTO search(String keyword, String sortBy, String direction, Long categoryId) {
        SearchResponseDTO response = new SearchResponseDTO();
        response.setQuery(keyword);

        log.info("--- SEARCH INITIATED ---");
        log.info("Query: '{}', Category: {}", keyword, categoryId);

        if (keyword == null || keyword.trim().isEmpty()) {
            response.setMessage("Please provide a search keyword");
            response.setResults(Collections.emptyList());
            return response;
        }

        // 1. Create a Case-Insensitive Regex (Supports partial matches like "Glo" for "Glow")
        String regexPattern = ".*" + keyword.trim() + ".*";
        Criteria searchCriteria = new Criteria().orOperator(
                Criteria.where("product_name").regex(regexPattern, "i"),
                Criteria.where("description").regex(regexPattern, "i")
        );

        Query query = new Query(searchCriteria);

        // 2. Add Category Filter (using nested path from your DB structure)
        if (categoryId != null) {
            query.addCriteria(Criteria.where("category.category_id").is(categoryId));
        }

        // 3. Add Sorting
        if (sortBy != null && !sortBy.isBlank()) {
            Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
            query.with(Sort.by(dir, sortBy));
        }

        try {
            // 4. Execute Query
            List<ProductSearchDocument> docs = mongoTemplate.find(query, ProductSearchDocument.class);
            log.info("CHECKPOINT: Documents found: {}", docs.size());

            List<ProductListingDTO> results = docs.stream()
                    .map(SearchResponseMapper::toDTO)
                    .collect(Collectors.toList());

            response.setResults(results);
            response.setTotalResults(results.size());
            response.setMessage(results.isEmpty() ? "No products found" : "Search successful");

        } catch (Exception e) {
            log.error("Search execution failed", e);
            response.setMessage("Search failed: " + e.getMessage());
        }

        return response;
    }

    @Override
    public List<ProductListingDTO> getSuggestions(String partialQuery) {
        // Simple autocomplete logic using starts-with regex
        Query query = new Query(Criteria.where("product_name").regex("^" + partialQuery, "i")).limit(5);
        return mongoTemplate.find(query, ProductSearchDocument.class)
                .stream()
                .map(SearchResponseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductListingDTO> getByCategory(Long categoryId) {
        // Fallback to repository for simple category listing
        return productSearchRepository.findByCategory_CategoryId(categoryId)
                .stream()
                .map(SearchResponseMapper::toDTO)
                .collect(Collectors.toList());
    }
}