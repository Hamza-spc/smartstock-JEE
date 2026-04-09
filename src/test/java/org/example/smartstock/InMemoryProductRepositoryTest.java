package org.example.smartstock;

import org.example.smartstock.domain.Product;
import org.example.smartstock.exception.ProductNotFoundException;
import org.example.smartstock.repository.InMemoryProductRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryProductRepositoryTest {

    @Test
    void shouldThrowWhenProductIsMissing() {
        InMemoryProductRepository repository = new InMemoryProductRepository();

        assertThrows(ProductNotFoundException.class, () -> repository.findBySku("MISSING-001"));
    }

    @Test
    void shouldReturnSavedProductBySku() {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        Product product = new Product("LAP-001", "Laptop", 10);

        repository.save(product);

        assertSame(product, repository.findBySku("LAP-001"));
    }
}
