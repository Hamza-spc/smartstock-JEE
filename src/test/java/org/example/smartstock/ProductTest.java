package org.example.smartstock;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void shouldCreateProductWithValidData() {
        Product product = new Product("LAP-001", "Laptop", 10);

        assertEquals("LAP-001", product.getSku());
        assertEquals("Laptop", product.getName());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void shouldRejectBlankSku() {
        assertThrows(IllegalArgumentException.class, () -> new Product(" ", "Laptop", 10));
    }

    @Test
    void shouldIncreaseStockWithPositiveAmount() {
        Product product = new Product("LAP-001", "Laptop", 10);

        product.increaseQuantity(5);

        assertEquals(15, product.getQuantity());
    }

    @Test
    void shouldRejectNonPositiveAmountWhenIncreasingStock() {
        Product product = new Product("LAP-001", "Laptop", 10);

        assertThrows(IllegalArgumentException.class, () -> product.increaseQuantity(0));
        assertThrows(IllegalArgumentException.class, () -> product.increaseQuantity(-2));
    }

    @Test
    void shouldDecreaseStockWithValidAmount() {
        Product product = new Product("LAP-001", "Laptop", 10);

        product.decreaseQuantity(3);

        assertEquals(7, product.getQuantity());
    }

    @Test
    void shouldRejectDecreasingBelowAvailableStock() {
        Product product = new Product("LAP-001", "Laptop", 10);

        assertThrows(InsufficientStockException.class, () -> product.decreaseQuantity(11));
    }
}
