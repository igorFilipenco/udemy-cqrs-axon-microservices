package com.udemy.payment.event;

import com.udemy.payment.core.data.entity.PaymentEntity;
import com.udemy.payment.core.data.repository.PaymentRepository;
import com.udemy.shared.event.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class PaymentEventsHandler {
    PaymentRepository repository;

    public PaymentEventsHandler(PaymentRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        repository.save(new PaymentEntity(event.getPaymentId(), event.getOrderId()));
        log.info("New payment registered");
    }
}
