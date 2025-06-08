package com.shopping.basket.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemQuantityUpdatedEvent extends BasketEvent {
    private String productId;
    private int quantity;

    public ItemQuantityUpdatedEvent(String basketId, String userId, String productId, int quantity, int version) {
        super(basketId, userId, "ITEM_QUANTITY_UPDATED", version);
        this.productId = productId;
        this.quantity = quantity;
    }
} 