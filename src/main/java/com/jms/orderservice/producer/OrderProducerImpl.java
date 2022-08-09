package com.jms.orderservice.producer;

import com.jms.orderservice.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class OrderProducerImpl implements OrderProducer {
    private static final String COUNTABLE_PRODUCT_ORDER_QUEUE = "countable.product.order.queue";
    private static final String LIQUID_PRODUCT_ORDER_QUEUE = "liquid.product.order.queue";

    private final JmsTemplate jmsTemplate;

    public OrderProducerImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Transactional
    public void sendOrder(List<Order> orders) {
        for (Order order : orders) {
            String queue = switch (order.productType()) {
                case COUNTABLE -> COUNTABLE_PRODUCT_ORDER_QUEUE;
                case LIQUID -> LIQUID_PRODUCT_ORDER_QUEUE;
            };

            jmsTemplate.convertAndSend(queue, order, message -> {
                message.setStringProperty("orderId", order.uuid().toString());
                message.setStringProperty("orderType", order.productType().name());
                message.setStringProperty("customerFullName", order.customer().fullName());
                return message;
            });
            log.info("Order was sent to queue = {}! {}", queue, order);
        }
    }
}
