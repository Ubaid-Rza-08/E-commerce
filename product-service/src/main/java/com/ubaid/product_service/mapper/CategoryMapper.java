package com.ubaid.product_service.mapper;

import com.ubaid.product_service.dto.CategoryRequest;
import com.ubaid.product_service.dto.CategoryResponse;
import com.ubaid.product_service.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toCategory(CategoryRequest categoryRequest){
        return Category.builder()
                .description(categoryRequest.description())
                .name(categoryRequest.name())
                .build();
    }
    public CategoryResponse fromCategory(Category category){
        return new CategoryResponse(
                category.getId(), category.getName(), category.getDescription()
        );
    }
}
