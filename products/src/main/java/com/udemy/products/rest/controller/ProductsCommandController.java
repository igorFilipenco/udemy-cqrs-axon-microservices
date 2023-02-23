package com.udemy.products.rest.controller;

import com.udemy.products.command.CreateProductCommand;
import com.udemy.products.rest.CreateProductRestModel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductsCommandController {

    private final Environment env;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProductsCommandController(Environment env, CommandGateway commandGateway, QueryGateway queryGateway) {
        this.env = env;
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel createProductRestModel) {
        CreateProductCommand command = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(createProductRestModel.getTitle())
                .price(createProductRestModel.getPrice())
                .quantity(createProductRestModel.getQuantity())
                .build();
        String result = "";

        try {
            result = commandGateway.sendAndWait(command);
        } catch (Exception e) {
            log.error("Product was not created");
        }

        return result;
    }

    @PutMapping
    public String updateProduct() {
        return "Product updated";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Product was deleted";
    }
}
