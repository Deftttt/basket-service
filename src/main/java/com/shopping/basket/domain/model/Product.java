package com.shopping.basket.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("product")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private int availableQuantity;

    public boolean hasAvailableQuantity(int requestedQuantity) {
        return availableQuantity >= requestedQuantity;
    }

    public void decreaseQuantity(int quantity) {
        if (!hasAvailableQuantity(quantity)) {
            throw new IllegalStateException("Not enough quantity available");
        }
        this.availableQuantity -= quantity;
    }

    public void increaseQuantity(int quantity) {
        this.availableQuantity += quantity;
    }
} 