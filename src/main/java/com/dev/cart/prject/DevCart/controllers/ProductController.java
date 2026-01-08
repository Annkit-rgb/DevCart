package com.dev.cart.prject.DevCart.controllers;


import com.dev.cart.prject.DevCart.models.Product;
import com.dev.cart.prject.DevCart.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

//Get All products

    @GetMapping("/api/public/products")
    public ResponseEntity<List<Product>> getAllProducts(){
       List<Product> products = productRepository.findAll();
       return ResponseEntity.ok().body(products);

    }


    // Get product by Id
    @GetMapping("/api/public/product/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());          // body is Product
        } else {
            return ResponseEntity
                    .status(404)
                    .body("Product Not Found with Id " + id); // body is String
        }
    }




}

