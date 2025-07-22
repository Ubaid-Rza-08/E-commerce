package com.ubaid.product_service.controller;

import com.ubaid.product_service.model.Product;
import com.ubaid.product_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product/cart")
public class CartController {
    private final CartService cartService;
    @GetMapping("/getCart")
    public List<Product> getCart(@AuthenticationPrincipal Jwt jwt) {
        return cartService.getCart(jwt);
    }
    @PostMapping("/addToCart")
    public ResponseEntity<String> addToCart(@RequestBody Long id, @AuthenticationPrincipal Jwt jwt){
        System.out.println(id);
        return  ResponseEntity.ok(cartService.addToCart(id, jwt));
    }
    @DeleteMapping("/removeFromCart")
    public void removeFromCart(@RequestBody Long id,@AuthenticationPrincipal Jwt jwt) {
        cartService.removeFromCart(id, jwt);
    }
}
