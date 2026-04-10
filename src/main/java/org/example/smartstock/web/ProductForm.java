package org.example.smartstock.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProductForm {
    @NotBlank
    @Size(max = 50)
    private String sku;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @PositiveOrZero
    private Integer quantity;

    public ProductForm(String sku, String name, Integer quantity) {
        this.sku = sku;
        this.name = name;
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
