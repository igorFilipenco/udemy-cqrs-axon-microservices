package com.udemy.orders.event;

import com.udemy.orders.core.data.enums.OrderStatus;
import lombok.*;


@Getter
@NoArgsConstructor
public class OrderApprovedEvent {
    private String orderId;
    private OrderStatus orderStatus;

    public OrderApprovedEvent(String orderId) {
        this.orderId = orderId;
        this.orderStatus = OrderStatus.APPROVED;
    }
}
