package org.example.smartstock.repository;

import org.example.smartstock.domain.Product;
import org.example.smartstock.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> productBySku = new HashMap<>();

    @Override
    public void save(Product product) {
        productBySku.put(product.getSku(),product);
    }

    @Override
    public Product findBySku(String sku) {
        Product product = productBySku.get(sku);
        if(product == null){
            throw new ProductNotFoundException("Product not found");
        }
        return product;
    }

    @Override
    public List<Product> findAll(){
        return new ArrayList<>(productBySku.values());
    }
}
