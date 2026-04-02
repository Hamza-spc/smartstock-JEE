package org.example.smartstock;

public class ApplicationStore {
    public static final ProductRepository PRODUCT_REPOSITORY = new InMemoryProductRepository();

    private ApplicationStore() {
    }
}
