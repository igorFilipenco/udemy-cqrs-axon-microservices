package com.udemy.orders.saga;

import com.udemy.orders.event.OrderCreatedEvent;
import com.udemy.shared.command.ReserveProductCommand;
import com.udemy.shared.event.ProductReservedEvent;
import com.udemy.shared.model.User;
import com.udemy.shared.query.FetchUserPaymentDetailsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


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
            //compensating transaction
            return;
        }

        if (Objects.isNull(userPaymentDetails)) {
            //compensating transaction
        }

        log.info("Successfully fetched user payment details for user with id= " + event.getUserId());
    }
}
