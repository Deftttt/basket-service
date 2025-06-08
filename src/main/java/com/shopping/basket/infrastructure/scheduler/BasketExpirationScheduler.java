package com.shopping.basket.infrastructure.scheduler;

import com.shopping.basket.domain.event.BasketExpiredEvent;
import com.shopping.basket.domain.model.Basket;
import com.shopping.basket.domain.model.BasketItem;
import com.shopping.basket.domain.service.BasketEventSourcingService;
import com.shopping.basket.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BasketExpirationScheduler {
    private final BasketEventSourcingService eventSourcingService;
    private final ProductService productService;

    @Value("${basket.expiration.minutes:2}")
    private int expirationMinutes;

    @Scheduled(fixedRateString = "${basket.expiration.check.interval:30000}")
    public void checkExpiredBaskets() {
        Instant expirationThreshold = Instant.now().minus(expirationMinutes, ChronoUnit.MINUTES);
        
        List<Basket> openBaskets = eventSourcingService.findOpenBaskets();
        for (Basket basket : openBaskets) {
            if (basket.getLastModified().isBefore(expirationThreshold)) {
                for (BasketItem item : basket.getItems().values()) {
                    try {
                        productService.increaseQuantity(item.getProductId(), item.getQuantity());
                    } catch (Exception ignored) {

                    }
                }

                BasketExpiredEvent event = new BasketExpiredEvent(
                    basket.getId(),
                    basket.getUserId(),
                    basket.getVersion() + 1
                );
                eventSourcingService.appendEvent(event);
            }
        }
    }
} 