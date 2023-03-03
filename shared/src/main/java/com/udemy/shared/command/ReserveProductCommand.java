package com.udemy.shared.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@Builder
public class ReserveProductCommand {
    @TargetAggregateIdentifier
    private String productId;
    private String orderId;
    private String userId;
    private int quantity;
}
