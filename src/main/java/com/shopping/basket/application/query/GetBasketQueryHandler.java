package com.shopping.basket.application.query;

import com.shopping.basket.api.dto.BasketItemResponse;
import com.shopping.basket.api.dto.BasketResponse;
import com.shopping.basket.domain.model.Basket;
import com.shopping.basket.domain.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetBasketQueryHandler {
    private final BasketService basketService;

    public BasketResponse handle(GetBasketQuery query) {
        Basket basket = basketService.getBasketForUser(query.getBasketId(), query.getUserId());

        List<BasketItemResponse> itemResponses = basket.getItems().values().stream()
                .map(item -> BasketItemResponse.builder()
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .totalPrice(item.getPrice() * item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return BasketResponse.builder()
                .basketId(basket.getId())
                .userId(basket.getUserId())
                .items(itemResponses)
                .totalValue(basket.getTotalValue())
                .status(basket.getStatus().toString())
                .build();
    }
} 