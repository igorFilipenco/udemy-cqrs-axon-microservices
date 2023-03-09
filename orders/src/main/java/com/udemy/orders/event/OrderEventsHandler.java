package com.udemy.orders.event;

import com.udemy.orders.core.data.OrderEntity;
import com.udemy.orders.core.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Slf4j
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

    @EventHandler
    public void on(OrderApprovedEvent approvedEvent) {
        OrderEntity order = repository.findByOrderId(approvedEvent.getOrderId());

        if (Objects.isNull(order)) {
            log.error("Error during approve order - no existing order with order id = " + approvedEvent.getOrderId());
        }

        order.setOrderStatus(approvedEvent.getOrderStatus());
        repository.save(order);
    }

    @EventHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        OrderEntity order = repository.findByOrderId(orderRejectedEvent.getOrderId());

        if (Objects.isNull(order)) {
            log.error("Error during rejecting order - no existing order with order id = " + orderRejectedEvent.getOrderId());
        }

        order.setOrderStatus(orderRejectedEvent.getOrderStatus());
        repository.save(order);
    }
}
