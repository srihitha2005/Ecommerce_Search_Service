//package com.example.SearchServiceApplication.service;
//import com.example.SearchServiceApplication.document.ProductSearchDocument;
//import com.example.SearchServiceApplication.dto.SearchResponseDTO;
//import com.example.SearchServiceApplication.repository.ProductSearchRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class SearchServiceTest {
//
//    @Mock
//    private ProductSearchRepository repository;
//
//    @InjectMocks
//    private SearchService searchService;
//
//    private ProductSearchDocument mockDoc;
//
//    @BeforeEach
//    void setUp() {
//        mockDoc = new ProductSearchDocument();
//        mockDoc.setStockId(1L);
//        mockDoc.setProductName("iPhone 15");
//        mockDoc.setDescription("Apple phone");
//        mockDoc.setPrice(new BigDecimal("99999.00"));
//        mockDoc.setCurrentStock(10);
//    }
//
//    @Test
//    @DisplayName("Should return results when valid keyword is provided")
//    void testSearch_Success() {
//        // Arrange
//        when(repository.hybridSearch("iphone")).thenReturn(List.of(mockDoc));
//
//        // Act
//        SearchResponseDTO response = searchService.search("iphone");
//
//        // Assert
//        assertEquals(1, response.getTotalResults());
//        assertEquals("Search results found", response.getMessage());
//        assertFalse(response.getResults().isEmpty());
//        assertEquals("iPhone 15", response.getResults().get(0).getProductName());
//
//        verify(repository, times(1)).hybridSearch("iphone");
//    }
//
//    @Test
//    @DisplayName("Should return empty results and specific message when no products match")
//    void testSearch_NoResults() {
//        // Arrange
//        when(repository.hybridSearch("unknown")).thenReturn(Collections.emptyList());
//
//        // Act
//        SearchResponseDTO response = searchService.search("unknown");
//
//        // Assert
//        assertEquals(0, response.getTotalResults());
//        assertEquals("No products found", response.getMessage());
//        assertTrue(response.getResults().isEmpty());
//    }
//
//    @Test
//    @DisplayName("Should handle null or blank keywords gracefully")
//    void testSearch_EmptyKeyword() {
//        // Act
//        SearchResponseDTO response = searchService.search("  ");
//
//        // Assert
//        assertEquals(0, response.getTotalResults());
//        assertEquals("Please provide a search keyword", response.getMessage());
//        verify(repository, never()).hybridSearch(anyString());
//    }
//}