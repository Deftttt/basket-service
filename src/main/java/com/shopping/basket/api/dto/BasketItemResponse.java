package com.shopping.basket.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketItemResponse {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private double totalPrice;
} 