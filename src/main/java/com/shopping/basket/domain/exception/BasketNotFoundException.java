package com.shopping.basket.domain.exception;

public class BasketNotFoundException extends RuntimeException {
    public BasketNotFoundException(String basketId) {
        super("Basket not found with id: " + basketId);
    }
} 