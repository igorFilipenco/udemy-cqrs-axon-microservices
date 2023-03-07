package com.udemy.payment.aggregate;


import com.udemy.shared.command.ProcessPaymentCommand;
import com.udemy.shared.event.PaymentProcessedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Objects;


@Data
@NoArgsConstructor
@Aggregate
public class PaymentAggregate {
    @AggregateIdentifier
    private String paymentId;
    private String orderId;

    @CommandHandler
    public PaymentAggregate(ProcessPaymentCommand command) {
        if (Objects.isNull(command.getPaymentId())) {
            throw new IllegalArgumentException("Error during execution of payment command - no payment id");
        }

        this.paymentId = command.getPaymentId();
        AggregateLifecycle.apply(new PaymentProcessedEvent(command.getOrderId(), command.getPaymentId()));
    }

    @EventSourcingHandler
    public void on(PaymentProcessedEvent paymentProcessedEvent) {
        this.paymentId = paymentProcessedEvent.getPaymentId();
        this.orderId = paymentProcessedEvent.getOrderId();
    }
}
