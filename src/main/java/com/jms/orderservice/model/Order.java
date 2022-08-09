package com.jms.orderservice.model;

import java.util.List;
import java.util.UUID;

public record Order(
        UUID uuid,
        Customer customer,
        List<OrderItem> orderItems,
        ProductType productType) {
}
