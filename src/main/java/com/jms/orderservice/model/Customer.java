package com.jms.orderservice.model;

import java.util.UUID;

public record Customer(
        UUID uuid,
        String fullName) {
}
