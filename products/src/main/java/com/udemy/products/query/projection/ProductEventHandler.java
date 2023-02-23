package com.udemy.products.query.projection;


import com.udemy.products.core.repository.data.ProductEntity;
import com.udemy.products.core.repository.entity.ProductsRepository;
import com.udemy.products.event.ProductCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler {
    ProductsRepository repository;

    public ProductEventHandler(ProductsRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity newProduct = new ProductEntity();
        BeanUtils.copyProperties(event, newProduct);
        repository.save(newProduct);
    }
}
