package com.udemy.products.core.data.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productLookup")
public class ProductLookupEntity implements Serializable {
    private static final long serialVersionUID = 2813181214954614378L;

    @Id
    @Column(unique = true)
    private String productId;
    @Column(unique = true)
    private String title;
}
