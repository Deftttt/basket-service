package com.shopping.basket.domain.exception;

public class BasketAccessDeniedException extends RuntimeException {
    public BasketAccessDeniedException(String basketId, String userId) {
        super("User " + userId + " does not have access to basket " + basketId);
    }
} 