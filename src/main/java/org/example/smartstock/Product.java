package org.example.smartstock;

public class Product {
    private String sku;
    private String name;
    private int quantity;

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
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

    public void decreaseQuantity(int amount){
        if (amount>quantity ){
            throw new InsufficientStockException("Not enough stock");
        }
        if(amount<=0){
            throw new IllegalArgumentException("amount should be positive");
        }
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount){
        if(amount > 0){
            this.quantity += amount;
        }
    }

}
