package com.shopping.basket.domain.exception;

public class InsufficientProductQuantityException extends RuntimeException {
    public InsufficientProductQuantityException(String productId, int requested, int available) {
        super(String.format("Insufficient quantity available for product %s. Requested: %d, Available: %d", 
                          productId, requested, available));
    }
} 