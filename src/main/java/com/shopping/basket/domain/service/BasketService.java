package com.shopping.basket.domain.service;

import com.shopping.basket.domain.event.*;
import com.shopping.basket.domain.exception.BasketAccessDeniedException;
import com.shopping.basket.domain.exception.BasketNotOpenException;
import com.shopping.basket.domain.model.Basket;
import com.shopping.basket.domain.model.BasketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketEventSourcingService eventSourcingService;

    public String createBasket(String userId) {
        String basketId = UUID.randomUUID().toString();
        BasketCreatedEvent event = new BasketCreatedEvent(basketId, userId, 1);
        eventSourcingService.appendEvent(event);
        return basketId;
    }

    public Basket getBasket(String basketId) {
        return eventSourcingService.reconstructBasket(basketId);
    }

    public Basket getBasketForUser(String basketId, String userId) {
        Basket basket = getBasket(basketId);
        if (!basket.getUserId().equals(userId)) {
            throw new BasketAccessDeniedException(basketId, userId);
        }
        return basket;
    }

    public void addItem(String basketId, String userId, String productId, String productName, 
                       int quantity, double price, Instant lockExpiration) {
        Basket basket = getBasketForUser(basketId, userId);
        validateBasketIsOpen(basket);

        ItemAddedEvent event = new ItemAddedEvent(basketId, userId, productId, productName, 
                                                quantity, price, lockExpiration, basket.getVersion() + 1);
        eventSourcingService.appendEvent(event);
    }

    public void removeItem(String basketId, String userId, String productId) {
        Basket basket = getBasketForUser(basketId, userId);
        validateBasketIsOpen(basket);

        ItemRemovedEvent event = new ItemRemovedEvent(basketId, userId, productId, basket.getVersion() + 1);
        eventSourcingService.appendEvent(event);
    }

    public void confirmBasket(String basketId, String userId) {
        Basket basket = getBasketForUser(basketId, userId);
        validateBasketIsOpen(basket);

        BasketConfirmedEvent event = new BasketConfirmedEvent(basketId, userId, basket.getVersion() + 1);
        eventSourcingService.appendEvent(event);
    }

    public void expireBasket(String basketId, String userId) {
        Basket basket = getBasket(basketId);
        if (basket != null && basket.getStatus() == BasketStatus.OPEN) {
            BasketExpiredEvent event = new BasketExpiredEvent(basketId, userId, basket.getVersion() + 1);
            eventSourcingService.appendEvent(event);
        }
    }

    public void updateItemQuantity(String basketId, String userId, String productId, int quantity) {
        Basket basket = getBasketForUser(basketId, userId);
        validateBasketIsOpen(basket);

        ItemQuantityUpdatedEvent event = new ItemQuantityUpdatedEvent(
            basketId, userId, productId, quantity, basket.getVersion() + 1
        );
        eventSourcingService.appendEvent(event);
    }

    private void validateBasketIsOpen(Basket basket) {
        if (basket.getStatus() != BasketStatus.OPEN) {
            throw new BasketNotOpenException(basket.getId());
        }
    }
} 