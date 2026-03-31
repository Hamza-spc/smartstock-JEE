package org.example.smartstock;

public interface ProductRepository {
    void save(Product product);
    Product findBySku(String sku);
}
