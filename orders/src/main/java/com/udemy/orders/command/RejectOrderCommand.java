package com.udemy.orders.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.AggregateIdentifier;


@Builder
@Data
public class RejectOrderCommand {
    @AggregateIdentifier
    private final String orderId;
    private final String reason;
}
