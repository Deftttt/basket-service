package com.shopping.basket.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    private String id;
    private String userId;
    private Map<String, BasketItem> items;
    private BasketStatus status;
    private int version;
    private Instant lastModified;


    public boolean isEmpty() {
        return items == null || items.isEmpty();
    }

    public double getTotalValue() {
        if (isEmpty()) {
            return 0.0;
        }
        return items.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
} 