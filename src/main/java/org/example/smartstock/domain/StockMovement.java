package org.example.smartstock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StockMovementType type;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected StockMovement() {
    }

    public StockMovement(StockMovementType type, int quantity, LocalDateTime createdAt, Product product) {
        if (type == null) {
            throw new IllegalArgumentException("Type is required");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (createdAt == null) {
            throw new IllegalArgumentException("Created at is required");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product is required");
        }
        this.type = type;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public StockMovementType getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Product getProduct() {
        return product;
    }
}
