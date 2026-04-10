package org.example.smartstock.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import org.example.smartstock.config.JpaRepo;
import org.example.smartstock.domain.Product;
import org.example.smartstock.repository.ProductRepository;

import java.util.List;

@Stateless
public class InventoryService {
    @Inject
    @JpaRepo
    private ProductRepository productRepository;

    @Inject
    private EntityManager entityManager;

    public InventoryService() {
    }

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void registerProduct(String sku, String name, int initialQuantity){
        executeInTransaction(() -> {
            Product product = new Product(sku, name, initialQuantity);
            productRepository.save(product);
        });
    }

    public Product getProduct(String sku){
        return productRepository.findBySku(sku);
    }

    public void addStock(String sku,int amount){
        executeInTransaction(() -> {
            Product product = productRepository.findBySku(sku);
            product.increaseQuantity(amount);
        });
    }

    public void removeStock(String sku,int amount){
        executeInTransaction(() -> {
            Product product = productRepository.findBySku(sku);
            product.decreaseQuantity(amount);
        });
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    private void executeInTransaction(Runnable action) {
        if (entityManager == null) {
            action.run();
            return;
        }

        try {
            entityManager.getTransaction().begin();
            action.run();
            entityManager.getTransaction().commit();
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }
}
