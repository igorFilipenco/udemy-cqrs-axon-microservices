package com.udemy.products.aggregate;

import com.udemy.products.command.CreateProductCommand;
import com.udemy.products.event.ProductCreatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;


@NoArgsConstructor
@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private int quantity;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createCommand) {
        if (createCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less than 0");
        }

        if (createCommand.getTitle() == null || createCommand.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title must not be empty");
        }

        if (createCommand.getQuantity() < 0 ) {
            throw new IllegalArgumentException("Quantity must not be less than 0");
        }

        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(createCommand, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.price = event.getPrice();
        this.quantity = event.getQuantity();
        this.title = event.getTitle();
    }
}
