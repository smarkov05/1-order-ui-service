package com.jms.orderservice.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@Data
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = LiquidOrderItem.class, name = "LiquidOrderItem"),
        @JsonSubTypes.Type(value = CountableOrderItem.class, name = "CountableOrderItem")
})
public abstract sealed class OrderItem permits LiquidOrderItem, CountableOrderItem {
    protected Product product;

    public abstract Number quantityOrderedProduct();
}
