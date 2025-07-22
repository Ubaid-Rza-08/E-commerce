package com.ubaid.product_service.mapper;

import com.ubaid.product_service.dto.ProductRequest;
import com.ubaid.product_service.dto.ProductResponse;
import com.ubaid.product_service.dto.PurchaseResponse;
import com.ubaid.product_service.model.Product;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {


    public Product toProduct(ProductRequest productRequest){
        return Product.builder()
                .price(productRequest.price())
                .name(productRequest.name())
                .stock(productRequest.stock())
                .description(productRequest.description())
                .image(productRequest.image())
                .ingredients(productRequest.ingredients())
                .build();
    }
    public ProductResponse fromProduct(Product product){
        return new ProductResponse(product.getId(),product.getName(), product.getImage(), product.getDescription(), product.getStock(),
                product.getPrice(), product.getIngredients());
    }
    public PurchaseResponse toPurchaseResponse(Product product,double quantity){
        return  new PurchaseResponse(
                product.getId(), product.getName(), product.getDescription(), product.getPrice(),quantity
        );
    }
}
