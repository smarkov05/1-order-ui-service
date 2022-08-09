package com.jms.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public final class LiquidProduct extends Product {

    public LiquidProduct(String name, String measuringUnit) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.measuringUnit = measuringUnit;
        this.type = ProductType.LIQUID;
    }

    @Override
    public String toString() {
        return "LiquidProduct{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", measuringUnit='" + measuringUnit + '\'' +
                '}';
    }
}
