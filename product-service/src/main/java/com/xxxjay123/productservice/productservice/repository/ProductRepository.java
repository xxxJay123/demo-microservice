package com.xxxjay123.productservice.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xxxjay123.productservice.productservice.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
