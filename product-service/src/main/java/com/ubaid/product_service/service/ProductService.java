package com.ubaid.product_service.service;

import com.ubaid.product_service.dto.*;
import com.ubaid.product_service.exception.CategoryNotFoundException;
import com.ubaid.product_service.exception.ProductPurchaseException;
import com.ubaid.product_service.mapper.CategoryMapper;
import com.ubaid.product_service.mapper.ProductMapper;
import com.ubaid.product_service.model.Category;
import com.ubaid.product_service.model.Product;
import com.ubaid.product_service.repository.CategoryRepository;
import com.ubaid.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;
    public Category getCategory(Long id){
        return categoryRepository.findById(id).orElseThrow(()->{
            return new CategoryNotFoundException("Category not found with the give ID: "+id);
        });
    }
    public Optional<ProductResponse> getProductById(Long id){
        return Optional.ofNullable(productRepository.findById(id).map(productMapper::fromProduct).orElseThrow(() -> {
            return new ProductPurchaseException("Product not found with the give ID: " + id);
        }));
    }
    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream().map(productMapper::fromProduct).toList();
    }
    public List<ProductResponse>getProductByCategory(Long id){
        if(id==0){
            return getAllProducts();
        }
        return productRepository.findAllByCategoryId(id);
    }
    public Category createCategory(CategoryRequest categoryRequest){
        Category category= categoryMapper.toCategory(categoryRequest);
        return categoryRepository.save(category);
    }
    public ProductResponse addProductToCategory(Long id, ProductRequest productRequest){
        Category category=getCategory(id);
        Product  product=productMapper.toProduct(productRequest);
        product.setCategory(category);
        category.getProductList().add(product);
        categoryRepository.save(category);
        return productMapper.fromProduct(product);
    }
    public List<ProductResponse> addProductListToCategory(Long id, List<ProductRequest> productRequestList){
        Category category=getCategory(id);
        List<ProductResponse> productresponse=new ArrayList<>();
        for(ProductRequest req:productRequestList){
            Product product=productMapper.toProduct(req);
            product.setCategory(category);
            category.getProductList().add(product);
            productresponse.add(productMapper.fromProduct(product));
        }
        categoryRepository.save(category);
        return productresponse;
    }
    public void DeleteCategory(Long id){
        getCategory(id);
        categoryRepository.deleteById(id);
    }
    public void DeleteProduct(Long id){
        productRepository.deleteById(id);
    }

    @Transactional(rollbackOn=ProductPurchaseException.class)
    public List<PurchaseResponse>purchaseProduct(List<PurchaseRequest>request ) throws ProductPurchaseException {
        var productIds=request
                .stream().map(PurchaseRequest::productId )
                .toList();
        var storedProducts=productRepository.findAllByIdInOrderById(productIds);
        if(productIds.size()!= storedProducts.size()){
            throw new ProductPurchaseException("One or more product does not exist");
        }
        var sortedRequest=request.stream().sorted(
                Comparator.comparing(PurchaseRequest::productId)
        ).toList();
        var purchasedProduct=new ArrayList<PurchaseResponse>();
        for(int i=0;i< storedProducts.size();i++){
            var product=storedProducts.get(i);
            var productRequest=sortedRequest.get(i);
            if(product.getStock()< productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity");
            }
            var newstock=product.getStock()- productRequest.quantity();
            product.setStock(newstock);
            productRepository.save(product);
            purchasedProduct.add(productMapper.toPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProduct;
    }
}
