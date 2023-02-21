package com.udemy.products.rest;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @PostMapping
    public String createProduct() {
        return "Product created";
    }

    @GetMapping
    public String getProducts() {
        return "Products!";
    }

    @PutMapping
    public String updateProduct() {
        return "Product updated";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "Product was deleted";
    }
}
