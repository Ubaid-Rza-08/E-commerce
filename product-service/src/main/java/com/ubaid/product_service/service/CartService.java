package com.ubaid.product_service.service;


import com.ubaid.product_service.model.CartItem;
import com.ubaid.product_service.model.Product;
import com.ubaid.product_service.repository.CartRepository;
import com.ubaid.product_service.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public String addToCart(Long productId, Jwt jwt) {
        Map<String,Object> claims=jwt.getClaims();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartRepository.findByUserId((UUID) claims.get("sub"))
                .orElseGet(() -> {
                    CartItem newCart = new CartItem();
                    newCart.setUserId((UUID) claims.get("sub"));
                    return newCart;
                });

        if (!cartItem.getProducts().contains(product)) {
            cartItem.getProducts().add(product);
        }
        cartRepository.save(cartItem); // saves new or updated cart
        return "Product added to cart";
    }
    public List<Product> getCart(Jwt jwt){
        Map<String,Object>claims=jwt.getClaims();
        return cartRepository.findByUserId((UUID) claims.get("sub")).orElseThrow(()->
                new RuntimeException("cart not found")).getProducts();

    }
    public void removeFromCart(Long id,Jwt jwt){
        Map<String,Object>claims=jwt.getClaims();
        CartItem cartItem=cartRepository.findByUserId((UUID) claims.get("sub")).orElseThrow(()->
                new RuntimeException("Cart NotFound")
        );
        boolean removed=cartItem.getProducts().removeIf(
                product -> product.getId().equals(id)
        );
        if(removed){
            cartRepository.save(cartItem);
        }else{
            throw new RuntimeException("Product not found in cart ");
        }

    }
}
