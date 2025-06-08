package com.shopping.basket.application.query;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetBasketQuery {
    String basketId;
    String userId;
} 