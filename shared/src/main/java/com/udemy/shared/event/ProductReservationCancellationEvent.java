package com.udemy.shared.event;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProductReservationCancellationEvent {
    private String productId;
    private String orderId;
    private String userId;
    private int quantity;
    private String reason;
}
