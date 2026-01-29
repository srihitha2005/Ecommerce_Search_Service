package com.example.SearchServiceApplication.controller;

import com.example.SearchServiceApplication.dto.ProductListingDTO;
import com.example.SearchServiceApplication.dto.SearchResponseDTO;
import com.example.SearchServiceApplication.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public ResponseEntity<SearchResponseDTO> search(
            @RequestParam(name = "q", required = false) String keyword,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(searchService.search(keyword, sortBy, direction, categoryId));
    }

    @GetMapping("/suggest")
    public ResponseEntity<List<ProductListingDTO>> suggest(@RequestParam(name = "q") String q) {
        return ResponseEntity.ok(searchService.getSuggestions(q));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductListingDTO>> filterByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(searchService.getByCategory(id));
    }
}