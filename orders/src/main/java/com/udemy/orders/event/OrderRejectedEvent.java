package com.udemy.orders.event;

import com.udemy.orders.core.data.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class OrderRejectedEvent {
    private String orderId;
    private String reason;
    private OrderStatus orderStatus = OrderStatus.REJECTED;
}
