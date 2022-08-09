package com.jms.orderservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public final class CountableProduct extends Product implements Serializable {

    public CountableProduct(String name, String measuringUnit) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.measuringUnit = measuringUnit;
        this.type = ProductType.COUNTABLE;
    }

    @Override
    public String toString() {
        return "CountableProduct{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", measuringUnit='" + measuringUnit + '\'' +
                '}';
    }
}
