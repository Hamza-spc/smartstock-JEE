package org.example.smartstock;

public class Product {
    private String sku;
    private String name;
    private int quantity;

    public Product(String sku,String name,int quantity){
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

    public void decreaseQuantity(int amount){
        if (amount>quantity || amount<=0){
            throw new InsufficientStockException("Not enough stock");
        }
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount){
        if(amount > 0){
            this.quantity += amount;
        }
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
