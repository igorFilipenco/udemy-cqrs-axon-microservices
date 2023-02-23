package com.udemy.products.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CreateProductRestModel {
    @NonNull
    @NotBlank(message = "Title required not to be empty")
    private String title;
    @Min(value = 1, message = "Price should be higher than 0")
    private BigDecimal price;
    @Min(value = 1, message = "Quantity cannot be less than 1")
    @Max(value = 5, message = "Quantity cannot be more than 5")
    private int quantity;
}
