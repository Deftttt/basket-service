package com.shopping.basket.application.command;

import com.shopping.basket.domain.model.Basket;
import com.shopping.basket.domain.model.BasketItem;
import com.shopping.basket.domain.service.BasketService;
import com.shopping.basket.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveItemCommandHandler {
    private final BasketService basketService;
    private final ProductService productService;

    public void handle(RemoveItemCommand command) {
        // Get the basket and validate access
        Basket basket = basketService.getBasketForUser(command.getBasketId(), command.getUserId());
        BasketItem item = basket.getItems().get(command.getProductId());
        
        if (item != null) {
            // First remove the item from the basket
            basketService.removeItem(
                command.getBasketId(),
                command.getUserId(),
                command.getProductId()
            );
            
            // Then increase the product quantity
            productService.increaseQuantity(command.getProductId(), item.getQuantity());
        }
    }
} 