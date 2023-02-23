package com.udemy.products.core.repository;

import com.udemy.products.core.data.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, String> {
    ProductEntity findByProductId(String id);

//    ProductEntity findProductByIdOrTitle(String id, String title);
}
