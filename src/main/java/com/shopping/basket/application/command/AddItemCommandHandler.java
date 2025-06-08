package com.shopping.basket.application.command;

import com.shopping.basket.domain.model.Product;
import com.shopping.basket.domain.service.BasketService;
import com.shopping.basket.domain.service.ProductService;
import com.shopping.basket.domain.exception.InsufficientProductQuantityException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddItemCommandHandler {
    private final BasketService basketService;
    private final ProductService productService;

    public void handle(AddItemCommand command) {
        Product product = productService.getProduct(command.getProductId());
        
        if (!product.hasAvailableQuantity(command.getQuantity())) {
            throw new InsufficientProductQuantityException(product.getId(), command.getQuantity(), product.getAvailableQuantity());
        }

        basketService.addItem(
            command.getBasketId(),
            command.getUserId(),
            product.getId(),
            product.getName(),
            command.getQuantity(),
            product.getPrice(),
            null
        );

        productService.checkAndDecreaseQuantity(product.getId(), command.getQuantity());
    }
} 