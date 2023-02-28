package com.udemy.orders.event;

import com.udemy.orders.core.data.OrderEntity;
import com.udemy.orders.core.repository.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class OrderEventsHandler {
    OrderRepository repository;

    public OrderEventsHandler(OrderRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity newOrder = new OrderEntity();
        BeanUtils.copyProperties(event, newOrder);
        repository.save(newOrder);
    }
}
