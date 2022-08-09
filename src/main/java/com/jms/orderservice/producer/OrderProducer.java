package com.jms.orderservice.producer;

import com.jms.orderservice.model.Order;

import java.util.List;

public interface OrderProducer {

    void sendOrder(List<Order> orders);
}
