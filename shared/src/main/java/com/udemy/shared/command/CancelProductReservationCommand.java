package com.udemy.shared.command;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@Builder
public class CancelProductReservationCommand {
    @TargetAggregateIdentifier
    private final String productId;
    private final String orderId;
    private final String userId;
    private final String reason;
    private final int quantity;
}
