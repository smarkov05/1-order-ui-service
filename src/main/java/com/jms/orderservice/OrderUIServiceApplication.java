package com.jms.orderservice;

import com.jms.orderservice.service.OrderUIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class OrderUIServiceApplication {
    private final OrderUIService orderUIService;

    @Autowired
    public OrderUIServiceApplication(OrderUIService orderUIService) {
        this.orderUIService = orderUIService;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderUIServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runOrderService() {
        orderUIService.runOrderUI();
    }
}
