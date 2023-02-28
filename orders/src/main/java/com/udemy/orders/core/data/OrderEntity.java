package com.udemy.orders.core.data;


import com.udemy.orders.core.data.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
public class OrderEntity implements Serializable {
    private static final long serialVersionUID = 9087307902728549144L;

    @Id
    @Column(unique = true)
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
