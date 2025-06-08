package com.shopping.basket.domain.event;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemRemovedEvent extends BasketEvent {
    private String productId;

    public ItemRemovedEvent(String basketId, String userId, String productId, int version) {
        super(basketId, userId, "ITEM_REMOVED", version);
        this.productId = productId;
    }
} 