package com.udemy.products.core.data;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
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
    @Column(unique = true)
    private String title;
    private BigDecimal price;
    private int quantity;
}
