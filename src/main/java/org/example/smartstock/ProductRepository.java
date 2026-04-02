package org.example.smartstock;

import java.util.List;

public interface ProductRepository {
    void save(Product product);
    Product findBySku(String sku);
    List<Product> findAll();
}
