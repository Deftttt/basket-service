package com.shopping.basket.api;

import com.shopping.basket.api.dto.AddItemRequest;
import com.shopping.basket.api.dto.BasketResponse;
import com.shopping.basket.api.dto.CreateBasketResponse;
import com.shopping.basket.api.dto.UpdateItemQuantityRequest;
import com.shopping.basket.application.command.*;
import com.shopping.basket.application.query.GetBasketQuery;
import com.shopping.basket.application.query.GetBasketQueryHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {
    private final CreateBasketCommandHandler createBasketCommandHandler;
    private final AddItemCommandHandler addItemCommandHandler;
    private final RemoveItemCommandHandler removeItemCommandHandler;
    private final UpdateItemQuantityCommandHandler updateItemQuantityCommandHandler;
    private final GetBasketQueryHandler getBasketQueryHandler;
    private final ConfirmBasketCommandHandler confirmBasketCommandHandler;

    @PostMapping
    public ResponseEntity<CreateBasketResponse> createBasket(@RequestHeader("X-User-Id") String userId) {
        String basketId = createBasketCommandHandler.handle(CreateBasketCommand.builder()
                .userId(userId)
                .build());
        return ResponseEntity.ok(CreateBasketResponse.builder()
                .basketId(basketId)
                .build());
    }

    @PostMapping("/{basketId}/items")
    public ResponseEntity<Void> addItem(@PathVariable String basketId,
                                      @RequestHeader("X-User-Id") String userId,
                                      @RequestBody @Valid AddItemRequest request) {
        addItemCommandHandler.handle(AddItemCommand.builder()
                .basketId(basketId)
                .userId(userId)
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{basketId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable String basketId,
                                         @PathVariable String productId,
                                         @RequestHeader("X-User-Id") String userId) {
        removeItemCommandHandler.handle(RemoveItemCommand.builder()
                .basketId(basketId)
                .userId(userId)
                .productId(productId)
                .build());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{basketId}")
    public ResponseEntity<BasketResponse> getBasket(@PathVariable String basketId,
                                                  @RequestHeader("X-User-Id") String userId) {
        BasketResponse response = getBasketQueryHandler.handle(GetBasketQuery.builder()
                .basketId(basketId)
                .userId(userId)
                .build());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{basketId}/confirm")
    public ResponseEntity<Void> confirmBasket(@PathVariable String basketId,
                                            @RequestHeader("X-User-Id") String userId) {
        confirmBasketCommandHandler.handle(ConfirmBasketCommand.builder()
                .basketId(basketId)
                .userId(userId)
                .build());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{basketId}/items/{productId}")
    public ResponseEntity<Void> updateItemQuantity(
            @PathVariable String basketId,
            @PathVariable String productId,
            @RequestHeader("X-User-Id") String userId,
            @RequestBody @Valid UpdateItemQuantityRequest request) {
        
        updateItemQuantityCommandHandler.handle(UpdateItemQuantityCommand.builder()
                .basketId(basketId)
                .userId(userId)
                .productId(productId)
                .quantity(request.getQuantity())
                .build());
        
        return ResponseEntity.ok().build();
    }
} 