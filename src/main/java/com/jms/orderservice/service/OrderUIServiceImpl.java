package com.jms.orderservice.service;

import com.jms.orderservice.model.CountableOrderItem;
import com.jms.orderservice.model.CountableProduct;
import com.jms.orderservice.model.Customer;
import com.jms.orderservice.model.LiquidOrderItem;
import com.jms.orderservice.model.LiquidProduct;
import com.jms.orderservice.model.Order;
import com.jms.orderservice.model.OrderItem;
import com.jms.orderservice.model.Product;
import com.jms.orderservice.producer.OrderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderUIServiceImpl implements OrderUIService {
    private final String errorMessage = "ATTENTION!!! You enter inappropriate data! Please follow the instructions";
    private final OrderProducer orderProducer;
    private final Scanner scanner;
    private Boolean repeatAfterError;


    public OrderUIServiceImpl(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
        scanner = new Scanner(System.in);
        repeatAfterError = false;
    }

    public void runOrderUI() {
        while (true) {
            repeatAfterError = false;
            System.out.println("Test order flow");
            System.out.println("Before you can place an order, you must register");
            Customer customer = registerCustomer();
            List<Order> orders = createOrders(customer);

            orderProducer.sendOrder(orders);
        }
    }

    private Customer registerCustomer() {
        String fullName;
        System.out.println("Enter your full name");

        do {
            fullName = scanner.nextLine();
        } while (fullName.isEmpty());

        return CustomerStorageService.addCustomer(fullName);
    }

    private List<Order> createOrders(Customer customer) {
        List<OrderItem> countableOrderItems = new ArrayList<>();
        List<OrderItem> liquidOrderItems = new ArrayList<>();
        int additionalProductChoice = Integer.MIN_VALUE;

        do {
            repeatAfterError = false;
            System.out.println("Choose a product by ordinal number");
            System.out.println(ProductStorageService.getListProducts());

            if (!scanner.hasNextInt()) {
                handleError();
                scanner.next();
                continue;
            }

            int productChoice = scanner.nextInt();
            if (!ProductStorageService.existKey(productChoice)) {
                handleError();
                continue;
            }

            Product product = ProductStorageService.getProduct(productChoice);
            System.out.printf("Enter quantity of %s. Measuring unit is %s\n", product.getName(), product.getMeasuringUnit());
            switch (product.getType()) {
                case LIQUID -> {
                    if (!scanner.hasNextDouble()) {
                        handleError();
                        scanner.next();
                        continue;
                    }

                    double productVolume = scanner.nextDouble();
                    if (productVolume <= 0) {
                        handleError();
                        continue;
                    }

                    liquidOrderItems.add(new LiquidOrderItem((LiquidProduct) product, productVolume));
                }
                case COUNTABLE -> {
                    if (!scanner.hasNextInt()) {
                        handleError();
                        scanner.next();
                        continue;
                    }
                    int productAmount = scanner.nextInt();
                    if (productAmount < 1) {
                        handleError();
                        continue;
                    }
                    countableOrderItems.add(new CountableOrderItem((CountableProduct) product, productAmount));
                }
            }
            System.out.println("""
                    Do you want to add another product?
                    Press 1 to add
                    Press 2 to send order""");

            if (!scanner.hasNextInt()) {
                handleError();
                continue;
            }
            additionalProductChoice = scanner.nextInt();
            if (additionalProductChoice < 0) {
                handleError();
            }

        } while (repeatAfterError || 1 == additionalProductChoice);
        System.out.println("List ordered products");

        List<List<OrderItem>> orderItems = List.of(countableOrderItems, liquidOrderItems);

        printOrderedProducts(orderItems);

        return orderItems.stream()
                .filter(Predicate.not(List::isEmpty))
                .map(listOrderItems ->
                        new Order(
                                UUID.randomUUID(),
                                customer,
                                listOrderItems,
                                listOrderItems.get(0).getProduct().getType()))
                .toList();
    }

    private void handleError() {
        System.out.println(errorMessage);
        repeatAfterError = true;
    }

    private void printOrderedProducts(List<List<OrderItem>> listOrders) {
        String orderedProducts = listOrders.stream()
                .flatMap(Collection::stream)
                .map(oi -> "%s - %s %s".formatted(
                        oi.getProduct().getName(),
                        oi.quantityOrderedProduct(),
                        oi.getProduct().getMeasuringUnit()))
                .collect(Collectors.joining(";\n"));
        System.out.println(orderedProducts);
    }
}
