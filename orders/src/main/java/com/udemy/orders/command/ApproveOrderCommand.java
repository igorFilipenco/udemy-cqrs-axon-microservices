package com.udemy.orders.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@AllArgsConstructor
public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;
}
