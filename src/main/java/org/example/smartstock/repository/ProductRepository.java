package org.example.smartstock.repository;

import org.example.smartstock.domain.Product;

import java.util.List;

public interface ProductRepository {
    void save(Product product);
    Product findBySku(String sku);
    List<Product> findAll();
}
