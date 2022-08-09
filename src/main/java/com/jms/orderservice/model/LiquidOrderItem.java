package com.jms.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public final class LiquidOrderItem extends OrderItem implements Serializable {
    private double productVolume;

    public LiquidOrderItem(LiquidProduct product, double productVolume) {
        this.product = product;
        this.productVolume = productVolume;
    }

    @Override
    public Number quantityOrderedProduct() {
        return productVolume;
    }

    @Override
    public String toString() {
        return "LiquidOrderItem{" +
                "productVolume=" + productVolume +
                ", product=" + product +
                '}';
    }
}
