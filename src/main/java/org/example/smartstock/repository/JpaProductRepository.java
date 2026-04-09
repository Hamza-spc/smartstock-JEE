package org.example.smartstock.repository;

import jakarta.persistence.EntityManager;

import java.util.List;

import org.example.smartstock.domain.Product;
import org.example.smartstock.exception.ProductNotFoundException;

public class JpaProductRepository implements ProductRepository {
    private final EntityManager entityManager;

    public JpaProductRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Product product) {
        entityManager.persist(product);
    }

    @Override
    public Product findBySku(String sku) {
        List<Product> products = entityManager
                .createQuery("SELECT p FROM Product p WHERE p.sku = :sku", Product.class)
                .setParameter("sku", sku)
                .getResultList();

        if (products.isEmpty()) {
            throw new ProductNotFoundException("Product not found: " + sku);
        }

        return products.getFirst();
    }

    @Override
    public List<Product> findAll() {
        return entityManager
                .createQuery("SELECT p FROM Product p ORDER BY p.id", Product.class)
                .getResultList();
    }
}
