package org.example.smartstock;

import org.example.smartstock.domain.Product;
import org.example.smartstock.repository.InMemoryProductRepository;
import org.example.smartstock.repository.ProductRepository;
import org.example.smartstock.service.InventoryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryServiceTest {

    @Test
    void shouldRegisterAndRetrieveProduct() {
        ProductRepository repository = new InMemoryProductRepository();
        InventoryService service = new InventoryService(repository);

        service.registerProduct("LAP-001", "Laptop", 10);

        Product product = service.getProduct("LAP-001");

        assertEquals("LAP-001", product.getSku());
        assertEquals("Laptop", product.getName());
        assertEquals(10, product.getQuantity());
    }
}
