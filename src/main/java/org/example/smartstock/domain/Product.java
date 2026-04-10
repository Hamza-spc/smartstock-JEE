package org.example.smartstock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

import org.example.smartstock.exception.InsufficientStockException;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank
    @Size(max = 50)
    private String sku;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(max = 100)
    private String name;

    @Column(nullable = false)
    @PositiveOrZero
    private int quantity;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<StockMovement> stockMovements = new ArrayList<>();

    protected Product() {
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<StockMovement> getStockMovements() {
        return stockMovements;
    }

    public Product(String sku, String name, int quantity){
        if(sku==null || sku.isBlank()){
            throw new IllegalArgumentException("SKU is required");
        }
        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("Name is required");
        }
        if(quantity<0){
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.sku=sku;
        this.name=name;
        this.quantity=quantity;
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (amount > quantity) {
            throw new InsufficientStockException("Not enough stock");
        }
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantity += amount;
    }

    public void addStockMovement(StockMovement stockMovement) {
        stockMovements.add(stockMovement);
    }

}
