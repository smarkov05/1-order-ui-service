package com.jms.orderservice.service;

import com.jms.orderservice.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomerStorageService {
    private static final List<Customer> CUSTOMERS = new ArrayList<>();

    private CustomerStorageService() {
    }

    public static Customer addCustomer(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return null;
        }

        Optional<Customer> exist = CUSTOMERS.stream()
                .filter(c -> fullName.equals(c.fullName()))
                .findAny();

        if (exist.isEmpty()) {
            Customer customer = new Customer(UUID.randomUUID(), fullName);
            CUSTOMERS.add(customer);
            return customer;
        }

        return exist.get();
    }
}
