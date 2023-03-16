package com.udemy.orders.rest.controller;

import com.udemy.orders.command.CreateOrderCommand;
import com.udemy.orders.core.data.enums.OrderStatus;
import com.udemy.orders.query.FindOrderQuery;
import com.udemy.orders.rest.dto.RequestOrderDTO;
import com.udemy.orders.rest.dto.ResponseOrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
public class OrdersCommandController {

    private final Environment env;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public OrdersCommandController(Environment env, CommandGateway commandGateway, QueryGateway queryGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public ResponseOrderDTO createOrder(@Valid @RequestBody RequestOrderDTO order) {
        String orderId = UUID.randomUUID().toString();
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .addressId(order.getAddressId())
                .orderStatus(OrderStatus.CREATED)
                .build();

       SubscriptionQueryResult<ResponseOrderDTO, ResponseOrderDTO> result = queryGateway.subscriptionQuery(new FindOrderQuery(orderId),
                ResponseTypes.instanceOf(ResponseOrderDTO.class),
                ResponseTypes.instanceOf(ResponseOrderDTO.class));
       try{
           commandGateway.sendAndWait(createOrderCommand);
           return result.updates().blockFirst();
       } finally{
           result.close();
       }
    }

    @PutMapping
    public String updateOrder() {
        return "Order updated";
    }

    @DeleteMapping
    public String deleteOrder() {
        return "Order was deleted";
    }
}