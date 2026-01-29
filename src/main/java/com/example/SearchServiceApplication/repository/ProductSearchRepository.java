package com.example.SearchServiceApplication.repository;

import com.example.SearchServiceApplication.document.ProductSearchDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends MongoRepository<ProductSearchDocument, String> {

    @Query("{ '$text': { '$search': ?0 } }")
    List<ProductSearchDocument> searchByText(String keyword);

    @Query("{ '$or': [ " +
            "{ 'product_name': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'description': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'product_id': { '$regex': ?0, '$options': 'i' } }, " +
            "{ 'category.name': { '$regex': ?0, '$options': 'i' } } " +
            "] }")
    List<ProductSearchDocument> searchAcrossAllFields(String regex);

    // Use underscore to tell Spring to look inside the Category object for categoryId
    List<ProductSearchDocument> findByCategory_CategoryId(Long categoryId);
}