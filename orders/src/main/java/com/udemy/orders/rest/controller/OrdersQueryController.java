package com.udemy.orders.rest.controller;

import com.udemy.orders.core.data.OrderEntity;
import com.udemy.orders.query.FindOrderQuery;
import com.udemy.orders.rest.dto.RequestOrderDTO;
import com.udemy.orders.rest.dto.ResponseOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrdersQueryController {
    private final Environment env;
    private final QueryGateway queryGateway;

    public OrdersQueryController(Environment env, QueryGateway queryGateway) {
        this.env = env;
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{orderId}")
    public ResponseOrderDTO getOrder(@PathVariable String orderId) {
        OrderEntity orderEntity = new OrderEntity();

        try {
            orderEntity = queryGateway.query(new FindOrderQuery(orderId), OrderEntity.class).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }

        return new ResponseOrderDTO(orderEntity.getOrderId(), "", orderEntity.getOrderStatus());

    }

    @GetMapping
    public List<ResponseOrderDTO> getOrders() {
        return new ArrayList<>();
    }
}