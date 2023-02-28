package com.udemy.orders.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String productId;
    private int quantity;
    private String addressId;
}
