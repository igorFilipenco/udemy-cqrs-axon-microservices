package com.udemy.payment.core.data.repository;

import com.udemy.payment.core.data.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
}
