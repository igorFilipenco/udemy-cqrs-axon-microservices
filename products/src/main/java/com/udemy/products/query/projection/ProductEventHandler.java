package com.udemy.products.query.projection;

import com.udemy.products.core.data.ProductEntity;
import com.udemy.products.core.repository.ProductsRepository;
import com.udemy.products.event.ProductCreatedEvent;
import com.udemy.shared.event.ProductReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class ProductEventHandler {
    ProductsRepository repository;

    public ProductEventHandler(ProductsRepository repository) {
        this.repository = repository;
    }

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception e) throws Exception {
        throw e;
    }

    @ExceptionHandler(resultType = IllegalArgumentException.class)
    public void handle(IllegalArgumentException e) {
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductEntity newProduct = new ProductEntity();
        BeanUtils.copyProperties(event, newProduct);
        repository.save(newProduct);
    }

    @EventHandler
    public void on(ProductReservedEvent event) {
        ProductEntity updatedProduct = repository.findByProductId(event.getProductId());
        updatedProduct.setQuantity(updatedProduct.getQuantity() - event.getQuantity());
        log.info("Product reserved event was applied in event handler for product with id - " + event.getProductId());

        repository.save(updatedProduct);
    }
}
