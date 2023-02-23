package com.udemy.products.query;


import com.udemy.products.core.repository.ProductsRepository;
import com.udemy.products.rest.CreateProductRestModel;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ProductsQueryHandler {
    ProductsRepository repository;

    public ProductsQueryHandler(ProductsRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    List<CreateProductRestModel> getProducts(FindProductsQuery query) {
        List<CreateProductRestModel> result = new ArrayList<>();
        repository.findAll().forEach(product -> {
            CreateProductRestModel newModel = new CreateProductRestModel();
            BeanUtils.copyProperties(product, newModel);
            result.add(newModel);
        });
        return result;
    }
}
