package com.enfasis.onlineorders.dao;

import com.enfasis.onlineorders.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductDao extends JpaRepository<Product, Long> {
}
