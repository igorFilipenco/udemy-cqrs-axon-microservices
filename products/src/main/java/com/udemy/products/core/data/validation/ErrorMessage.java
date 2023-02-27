package com.udemy.products.core.data.validation;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@Data
@AllArgsConstructor
public class ErrorMessage {
    private final Date timestamp;
    private final String message;
}
