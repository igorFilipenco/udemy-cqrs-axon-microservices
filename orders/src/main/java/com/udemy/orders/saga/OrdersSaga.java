package com.udemy.orders.saga;

import com.udemy.orders.command.ApproveOrderCommand;
import com.udemy.orders.command.RejectOrderCommand;
import com.udemy.orders.event.OrderApprovedEvent;
import com.udemy.orders.event.OrderCreatedEvent;
import com.udemy.orders.event.OrderRejectedEvent;
import com.udemy.shared.command.CancelProductReservationCommand;
import com.udemy.shared.command.ProcessPaymentCommand;
import com.udemy.shared.command.ReserveProductCommand;
import com.udemy.shared.event.PaymentProcessedEvent;
import com.udemy.shared.event.ProductReservationCancellationEvent;
import com.udemy.shared.event.ProductReservedEvent;
import com.udemy.shared.model.User;
import com.udemy.shared.query.FetchUserPaymentDetailsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Slf4j
@Saga
public class OrdersSaga {
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .userId(event.getUserId())
                .quantity(event.getQuantity())
                .build();
        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                log.error("Something went wrong during product reserve: " + commandResultMessage.exceptionResult().getMessage());
            }
        });
        log.info("Created order command fired! Order id = " + event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        log.info("Handling product reserve event for product with id = " + event.getProductId());

        FetchUserPaymentDetailsQuery query = new FetchUserPaymentDetailsQuery(event.getUserId());
        User userPaymentDetails = null;

        try {
            userPaymentDetails = queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e) {
            cancelProductReservation(event, e.getMessage());
            return;
        }

        if (Objects.isNull(userPaymentDetails)) {
            cancelProductReservation(event, "[FAILURE] - Product reservation failed - user with id = " + event.getUserId() + " not found");
            return;
        }

        log.info("Successfully fetched user payment details for user with id= " + event.getUserId());

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(event.getOrderId())
                .paymentDetails(userPaymentDetails.getPaymentDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();

        String result = null;

        try {
            result = commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error(e.getMessage());
            cancelProductReservation(event, e.getMessage());
            return;
        }

        if (Objects.isNull(result)) {
            cancelProductReservation(event, "Somethiong wrong in process payment transaction for order with id = " + event.getOrderId());
            log.info("Something wrong in process payment transaction. Starting compensating transaction");
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent) {
        ApproveOrderCommand approveOrderCommand =
                new ApproveOrderCommand(paymentProcessedEvent.getOrderId());
        commandGateway.send(approveOrderCommand);
    }

    private void cancelProductReservation(ProductReservedEvent event, String reason) {
        CancelProductReservationCommand cancelReserveCommand = CancelProductReservationCommand.builder()
                .orderId(event.getOrderId())
                .userId(event.getUserId())
                .quantity(event.getQuantity())
                .productId(event.getProductId())
                .reason(reason)
                .build();
        commandGateway.send(cancelReserveCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservationCancellationEvent cancellationEvent) {
        commandGateway.send(RejectOrderCommand.builder()
                .orderId(cancellationEvent.getOrderId())
                .reason(cancellationEvent.getReason())
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderRejectedEvent orderRejectedEvent) {
        log.info("Order is rejected!");
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent) {
        log.info("Order is approved!");
    }
}
