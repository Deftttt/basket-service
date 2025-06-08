package com.shopping.basket.application.command;

import com.shopping.basket.domain.model.Basket;
import com.shopping.basket.domain.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmBasketCommandHandler {
    private final BasketService basketService;

    public void handle(ConfirmBasketCommand command) {
        Basket basket = basketService.getBasketForUser(command.getBasketId(), command.getUserId());

        if (basket.isEmpty()) {
            throw new IllegalArgumentException("Cannot confirm empty basket");
        }

        basketService.confirmBasket(
            command.getBasketId(),
            command.getUserId()
        );
    }
} 