package com.udemy.payment.core.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class PaymentEntity implements Serializable {
    private static final long serialVersionUID = -5281489873141529868L;

    @Id
    @Column(unique = true)
    private String paymentId;
    @Column
    public String orderId;
}
