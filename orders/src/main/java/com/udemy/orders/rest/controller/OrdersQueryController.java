package com.udemy.orders.rest.controller;

import com.udemy.orders.rest.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


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

    @GetMapping
    public List<OrderDTO> getOrders() {
        return new ArrayList<>();
    }
}