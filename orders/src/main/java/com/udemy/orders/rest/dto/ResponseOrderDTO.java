package com.udemy.orders.rest.dto;

import com.udemy.orders.core.data.enums.OrderStatus;
import lombok.Value;


@Value
public class ResponseOrderDTO {
    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus;
}