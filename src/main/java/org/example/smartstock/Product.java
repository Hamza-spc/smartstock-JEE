package org.example.smartstock;

public class Product {
    private String sku;
    private String name;
    private int quantity;

    public Product(String sku,String name,int quantity){
        if(sku==null || sku.isBlank()){
            throw new RuntimeException("SKU is required");
        }
        if(name==null || name.isBlank()){
            throw new RuntimeException("Name is required");
        }
        if(quantity<0){
            throw new RuntimeException("Quantity cannot be negative");
        }
        this.sku=sku;
        this.name=name;
        this.quantity=quantity;
    }

    public void decreaseQuantity(int amount){
        if (amount>quantity){
            throw new IllegalArgumentException("Not enough stock");
        }
        this.quantity -= amount;
    }

    public void increaseQuantity(int amount){
        this.quantity += amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
