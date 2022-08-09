package com.jms.orderservice.service;

import com.jms.orderservice.model.CountableProduct;
import com.jms.orderservice.model.LiquidProduct;
import com.jms.orderservice.model.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductStorageService {
    private final static Map<Integer, Product> PRODUCTS = new HashMap<>();

    static {
        PRODUCTS.put(1, new LiquidProduct("Water", "liter"));
        PRODUCTS.put(2, new LiquidProduct("Milk", "liter"));
        PRODUCTS.put(3, new LiquidProduct("Bear", "pint"));
        PRODUCTS.put(4, new LiquidProduct("Olive oil", "gallon"));

        PRODUCTS.put(5, new CountableProduct("Chocolate", "pcs"));
        PRODUCTS.put(6, new CountableProduct("Canned fish", "pickle"));
        PRODUCTS.put(7, new CountableProduct("Bread", "pcs"));
        PRODUCTS.put(8, new CountableProduct("Strawberry", "box"));
    }

    private ProductStorageService() {
    }

    public static String getListProducts() {
        return PRODUCTS.entrySet().stream()
                .map(entry -> "%d. %s".formatted(entry.getKey(), entry.getValue().getName()))
                .collect(Collectors.joining(";\n"));
    }

    public static Product getProduct(int key) {
        return PRODUCTS.get(key);
    }

    public static boolean existKey(int key) {
        return PRODUCTS.containsKey(key);
    }
}
