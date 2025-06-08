package com.shopping.basket.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ConfirmBasketCommand {
    private final String basketId;
    private final String userId;
} 