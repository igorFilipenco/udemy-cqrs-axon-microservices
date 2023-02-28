package com.udemy.orders.core.repository;

import com.udemy.orders.core.data.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, String> {
}
