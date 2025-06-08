package com.shopping.basket.domain.event;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.CLASS,
    include = JsonTypeInfo.As.PROPERTY,
    property = "@class"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BasketCreatedEvent.class),
    @JsonSubTypes.Type(value = BasketConfirmedEvent.class),
    @JsonSubTypes.Type(value = BasketExpiredEvent.class),
    @JsonSubTypes.Type(value = ItemAddedEvent.class),
    @JsonSubTypes.Type(value = ItemRemovedEvent.class),
    @JsonSubTypes.Type(value = ItemQuantityUpdatedEvent.class),
})
public interface Event {
    String getType();
    int getVersion();
} 