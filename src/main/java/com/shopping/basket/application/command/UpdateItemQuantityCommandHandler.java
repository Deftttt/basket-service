package com.shopping.basket.application.command;

import com.shopping.basket.domain.model.Basket;
import com.shopping.basket.domain.model.BasketItem;
import com.shopping.basket.domain.service.BasketService;
import com.shopping.basket.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateItemQuantityCommandHandler {
    private final BasketService basketService;
    private final ProductService productService;

    public void handle(UpdateItemQuantityCommand command) {
        Basket basket = basketService.getBasketForUser(command.getBasketId(), command.getUserId());

        BasketItem currentItem = basket.getItems().get(command.getProductId());
        if (currentItem == null) {
            throw new IllegalArgumentException("Product not found in basket");
        }
        if (command.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        if (command.getQuantity() > currentItem.getQuantity()) {
            int additionalQuantity = command.getQuantity() - currentItem.getQuantity();
            productService.checkAndDecreaseQuantity(command.getProductId(), additionalQuantity);
        } else if (command.getQuantity() < currentItem.getQuantity()) {
            int returnedQuantity = currentItem.getQuantity() - command.getQuantity();
            productService.increaseQuantity(command.getProductId(), returnedQuantity);
        }

        basketService.updateItemQuantity(
            command.getBasketId(),
            command.getUserId(),
            command.getProductId(),
            command.getQuantity()
        );
    }
} 