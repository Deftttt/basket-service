package com.shopping.basket.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketItem {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private Instant lockExpiration;


    public BasketItem withQuantity(int newQuantity) {
        return BasketItem.builder()
                .productId(this.productId)
                .productName(this.productName)
                .quantity(newQuantity)
                .price(this.price)
                .lockExpiration(this.lockExpiration)
                .build();
    }

} 