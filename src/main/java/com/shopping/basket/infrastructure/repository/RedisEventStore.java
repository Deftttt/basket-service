package com.shopping.basket.infrastructure.repository;

import com.shopping.basket.domain.event.BasketEvent;
import com.shopping.basket.domain.event.Event;
import com.shopping.basket.domain.repository.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RedisEventStore implements EventStore {
    private final RedisTemplate<String, BasketEvent> basketEventRedisTemplate;
    private static final String KEY_PREFIX = "events:basket:";

    @Override
    public void store(Event event) {
        if (!(event instanceof BasketEvent)) {
            throw new IllegalArgumentException("Only BasketEvents are supported");
        }
        String key = getKey(event);
        basketEventRedisTemplate.opsForList().rightPush(key, (BasketEvent) event);
    }

    @Override
    public List<Event> getEvents(String aggregateId) {
        String key = getKey(aggregateId);
        List<BasketEvent> events = basketEventRedisTemplate.opsForList().range(key, 0, -1);
        return events != null ? events.stream().map(e -> (Event) e).collect(Collectors.toList()) : List.of();
    }


    @Override
    public int getLatestVersion(String aggregateId) {
        List<Event> events = getEvents(aggregateId);
        return events.isEmpty() ? 0 : events.get(events.size() - 1).getVersion();
    }

    @Override
    public Set<String> getAllAggregateIds() {
        Set<String> keys = basketEventRedisTemplate.keys(KEY_PREFIX + "*");
        return keys != null ? keys.stream()
                .map(key -> key.substring(KEY_PREFIX.length()))
                .collect(Collectors.toSet()) : Set.of();
    }

    private String getKey(Event event) {
        if (event instanceof BasketEvent basketEvent) {
            return KEY_PREFIX + basketEvent.getBasketId();
        }
        throw new IllegalArgumentException("Unknown event type: " + event.getClass());
    }

    private String getKey(String aggregateId) {
        return KEY_PREFIX + aggregateId;
    }
} 