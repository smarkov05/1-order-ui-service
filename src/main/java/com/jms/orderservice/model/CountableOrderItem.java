package com.jms.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public final class CountableOrderItem extends OrderItem implements Serializable {
    private long amount;

    public CountableOrderItem(CountableProduct product, long amount) {
        this.product = product;
        this.amount = amount;
    }

    @Override
    public Number quantityOrderedProduct() {
        return amount;
    }

    @Override
    public String toString() {
        return "CountableOrderItem{" +
                "amount=" + amount +
                ", product=" + product +
                '}';
    }
}

