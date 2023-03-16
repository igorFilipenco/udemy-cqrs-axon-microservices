package com.udemy.orders.query;

import com.udemy.orders.core.data.OrderEntity;
import com.udemy.orders.core.repository.OrderRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;


@Component
public class OrderQueryHandler {
    OrderRepository orderRepository;

    public OrderQueryHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @QueryHandler
    public OrderEntity findOrder(FindOrderQuery findOrderQuery) {
        return orderRepository.findByOrderId(findOrderQuery.getOrderId());
    }
}
