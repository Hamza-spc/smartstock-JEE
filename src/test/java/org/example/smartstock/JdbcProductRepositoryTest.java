package org.example.smartstock;

import org.example.smartstock.config.ConnectionFactory;
import org.example.smartstock.config.FlywayMigrationRunner;
import org.example.smartstock.domain.Product;
import org.example.smartstock.repository.JdbcProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JdbcProductRepositoryTest {
    private ConnectionFactory connectionFactory;
    private JdbcProductRepository jdbcProductRepository;

    @BeforeEach
    void setUp() {
        connectionFactory = new ConnectionFactory("jdbc:h2:mem:smartstock-test;DB_CLOSE_DELAY=-1");
        FlywayMigrationRunner flywayMigrationRunner = new FlywayMigrationRunner(connectionFactory);
        flywayMigrationRunner.migrate();
        jdbcProductRepository = new JdbcProductRepository(connectionFactory);
    }

    @Test
    void shouldFindProductBySkuFromDatabase() {
        Product product = jdbcProductRepository.findBySku("LAP-001");

        assertEquals("LAP-001", product.getSku());
        assertEquals("Laptop", product.getName());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void shouldSaveProductToDatabase() {
        Product product = new Product("MON-001", "Monitor", 7);

        jdbcProductRepository.save(product);

        Product savedProduct = jdbcProductRepository.findBySku("MON-001");
        assertEquals("MON-001", savedProduct.getSku());
        assertEquals("Monitor", savedProduct.getName());
        assertEquals(7, savedProduct.getQuantity());
    }
}
