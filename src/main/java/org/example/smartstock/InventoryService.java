package org.example.smartstock;

import java.util.List;

public class InventoryService {
    private final ProductRepository productRepository;

    public InventoryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void registerProduct(String sku, String name, int initialQuantity){
        Product product = new Product(sku,name,initialQuantity);
        productRepository.save(product);
    }

    public Product getProduct(String sku){
        return productRepository.findBySku(sku);
    }

    public void addStock(String sku,int amount){
        Product product = productRepository.findBySku(sku);
        product.increaseQuantity(amount);
    }

    public void removeStock(String sku,int amount){
        Product product = productRepository.findBySku(sku);
        product.decreaseQuantity(amount);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
