package com.shopping.basket.application.command;

import com.shopping.basket.domain.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateBasketCommandHandler {
    private final BasketService basketService;

    public String handle(CreateBasketCommand command) {
        return basketService.createBasket(command.getUserId());
    }
} 