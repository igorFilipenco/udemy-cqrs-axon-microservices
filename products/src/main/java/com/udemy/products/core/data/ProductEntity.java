package com.udemy.products.core.data;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 3648382944086457725L;
    @Id
    @Column(unique = true)
    private String productId;
    @NonNull
    @Column(unique = true)
    private String title;
    @NonNull
    private BigDecimal price;
    private int quantity;
}
