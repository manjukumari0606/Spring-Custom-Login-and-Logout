package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
