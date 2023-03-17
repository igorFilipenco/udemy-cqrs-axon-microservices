package com.udemy.products.command;


import com.udemy.products.core.data.validation.ProductLookupEntity;
import com.udemy.products.core.repository.ProductLookupRepository;
import com.udemy.products.event.ProductCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.springframework.stereotype.Component;


@Component
@ProcessingGroup("product-group")
public class ProductLookupEventHandler {
    ProductLookupRepository repository;

    public ProductLookupEventHandler(ProductLookupRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookupEntity productLookup = new ProductLookupEntity(event.getProductId(), event.getTitle());

        repository.save(productLookup);
    }

    @ResetHandler
    public void reset() {
        repository.deleteAll();
    }
}
