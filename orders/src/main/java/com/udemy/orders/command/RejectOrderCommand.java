package com.udemy.orders.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Builder
@Data
public class RejectOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final String reason;
}
