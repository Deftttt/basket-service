package com.shopping.basket.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateItemQuantityCommand {
    private final String basketId;
    private final String userId;
    private final String productId;
    private final int quantity;
} 