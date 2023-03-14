package com.udemy.orders.event;

import com.udemy.orders.core.data.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRejectedEvent {
    private String orderId;
    private String reason;
    private OrderStatus orderStatus = OrderStatus.REJECTED;
}
