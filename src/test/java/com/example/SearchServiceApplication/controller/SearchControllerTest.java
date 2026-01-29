//package com.example.SearchServiceApplication.controller;
//import com.example.SearchServiceApplication.dto.SearchResponseDTO;
//import com.example.SearchServiceApplication.service.SearchService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(SearchController.class)
//class SearchControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private SearchService searchService;
//
//    @Test
//    void testSearchEndpoint() throws Exception {
//        SearchResponseDTO mockResponse = new SearchResponseDTO();
//        mockResponse.setQuery("iphone");
//        mockResponse.setTotalResults(1);
//
//        when(searchService.search("iphone")).thenReturn(mockResponse);
//
//        mockMvc.perform(get("/api/search").param("q", "iphone"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.query").value("iphone"))
//                .andExpect(jsonPath("$.totalResults").value(1));
//    }
//}