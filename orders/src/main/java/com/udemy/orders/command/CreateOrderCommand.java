package com.udemy.orders.command;

import com.udemy.orders.core.data.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;


@Builder
@Data
public class CreateOrderCommand {
    @AggregateIdentifier
    private final String orderId;
    private final String userId;
    private final String productId;
    private final int quantity;
    private String addressId;
    private final OrderStatus orderStatus;

}
