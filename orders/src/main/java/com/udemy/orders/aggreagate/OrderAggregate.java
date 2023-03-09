package com.udemy.orders.aggreagate;

import com.udemy.orders.command.ApproveOrderCommand;
import com.udemy.orders.command.CreateOrderCommand;
import com.udemy.orders.core.data.enums.OrderStatus;
import com.udemy.orders.event.OrderApprovedEvent;
import com.udemy.orders.event.OrderCreatedEvent;
import com.udemy.orders.event.OrderRejectedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


@NoArgsConstructor
@Aggregate
@Data
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        OrderCreatedEvent event = new OrderCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.productId = event.getProductId();
        this.quantity = event.getQuantity();
        this.addressId = event.getAddressId();
        this.orderStatus = event.getOrderStatus();
    }

    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand) {
        OrderApprovedEvent approvedEvent =
                new OrderApprovedEvent(approveOrderCommand.getOrderId());

        AggregateLifecycle.apply(approvedEvent);
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent) {
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        this.orderStatus = orderRejectedEvent.getOrderStatus();
    }
}
