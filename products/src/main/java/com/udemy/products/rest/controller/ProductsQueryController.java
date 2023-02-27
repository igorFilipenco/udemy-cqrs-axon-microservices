package com.udemy.products.rest.controller;


import com.udemy.products.query.FindProductsQuery;
import com.udemy.products.rest.CreateProductRestModel;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/products")
public class ProductsQueryController {
    private final Environment env;
    private final QueryGateway queryGateway;

    public ProductsQueryController(Environment env, QueryGateway queryGateway) {
        this.env = env;
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<CreateProductRestModel> getProducts() {
        FindProductsQuery query = new FindProductsQuery();

        return queryGateway.query(query,
                ResponseTypes.multipleInstancesOf(CreateProductRestModel.class)).join();
    }
}
