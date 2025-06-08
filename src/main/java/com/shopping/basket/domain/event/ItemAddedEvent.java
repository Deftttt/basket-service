package com.shopping.basket.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
public class ItemAddedEvent extends BasketEvent {
    private String productId;
    private String productName;
    private int quantity;
    private double price;
    private Instant lockExpiration;

    public ItemAddedEvent(String basketId, String userId, String productId, String productName, 
                         int quantity, double price, Instant lockExpiration, int version) {
        super(basketId, userId, "ITEM_ADDED", version);
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.lockExpiration = lockExpiration;
    }
} 