package com.ubaid.product_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String image;
    private String description;
    private double stock;
    private BigDecimal price;
    private String ingredients;
    @JsonIgnore
    @ManyToMany(mappedBy = "products" ,cascade = CascadeType.ALL)
    private List<CartItem>cartItems=new ArrayList<>();
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;



}
