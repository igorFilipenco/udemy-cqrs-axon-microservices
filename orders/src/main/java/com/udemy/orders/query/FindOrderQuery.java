package com.udemy.orders.query;

import lombok.Builder;
import lombok.Value;


@Value
//@Builder
public class FindOrderQuery {
    private final String orderId;
}
