package com.example.SearchServiceApplication.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class ProductSearchDocument {
    @Id
    private String id;
    @Field("product_id")
    private String productId;
    @Field("product_name")
    private String productName;
    @Field("description")
    private String description;
    private Integer rating;
    private Category category;

    @Data
    public static class Category {
        @Field("category_id")
        private Long categoryId;
        private String name;
    }
}