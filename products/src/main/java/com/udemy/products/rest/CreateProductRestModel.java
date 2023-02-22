package com.udemy.products.rest;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CreateProductRestModel {
    private String title;
    private BigDecimal price;
    private int quantity;
}
