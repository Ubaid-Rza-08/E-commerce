package com.ubaid.product_service.controller;

import com.ubaid.product_service.dto.*;
import com.ubaid.product_service.model.Category;
import com.ubaid.product_service.model.Product;
import com.ubaid.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductResponse>> getProductById(@RequestParam("id") Long id){
        return ResponseEntity.ok(service.getProductById(id));
    }
    @GetMapping("/getCategory")
    public ResponseEntity<Category> getCategory(@RequestParam("categoryId") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getCategory(id));
    }
    @GetMapping ("/getProductByCategoryId")
    public ResponseEntity<List<ProductResponse>>getProductByCategoryId(@RequestParam("id")Long id){
        return ResponseEntity.ok(service.getProductByCategory(id));
    }

    @PostMapping("/createCategory")
    public ResponseEntity<Category>createCategory(@RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCategory(categoryRequest));
    }
    @PostMapping("/addProductToCategory")
    public ResponseEntity <ProductResponse> addProductToCategory(@RequestParam("categoryId") Long id, @RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.addProductToCategory(id,productRequest));
    }
    @PostMapping("/addProductListToCategory")
    public ResponseEntity<List<ProductResponse>> addProductListToCategory(@RequestParam("categoryId") Long id,
                                                                          @RequestBody  List<ProductRequest> productRequestList) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.addProductListToCategory(id, productRequestList));
    }
    @PostMapping("/purchase")
    public ResponseEntity<List<PurchaseResponse>> purchaseProduct(
            @RequestBody List<PurchaseRequest>req
    ) {
        return ResponseEntity.ok(service.purchaseProduct(req));
    }
    @DeleteMapping("/deleteCategory")
    public ResponseEntity deleteCategory(@RequestParam("category_id") Long id){
        service.DeleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/deleteProduct")
    public ResponseEntity deleteProdcut(@RequestParam("category_id") Long id){
        service.DeleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
