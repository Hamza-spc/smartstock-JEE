package org.example.smartstock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.smartstock.config.ConnectionFactory;
import org.example.smartstock.config.EntityManagerFactoryProvider;
import org.example.smartstock.config.FlywayMigrationRunner;
import org.example.smartstock.domain.Product;
import org.example.smartstock.domain.StockMovement;
import org.example.smartstock.domain.StockMovementType;
import org.example.smartstock.repository.JpaProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JpaProductRepositoryTest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaProductRepository jpaProductRepository;

    @BeforeEach
    void setUp() {
        String testDbUrl = "jdbc:h2:mem:smartstock-jpa-test-" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1;MODE=LEGACY";
        ConnectionFactory connectionFactory = new ConnectionFactory(testDbUrl);
        FlywayMigrationRunner flywayMigrationRunner = new FlywayMigrationRunner(connectionFactory);
        flywayMigrationRunner.migrate();

        EntityManagerFactoryProvider provider = new EntityManagerFactoryProvider();
        entityManagerFactory = provider.create(Map.of(
                "jakarta.persistence.jdbc.url", testDbUrl,
                "jakarta.persistence.jdbc.user", "sa",
                "jakarta.persistence.jdbc.password", ""
        ));
        entityManager = entityManagerFactory.createEntityManager();
        jpaProductRepository = new JpaProductRepository(entityManager);
    }

    @AfterEach
    void tearDown() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    void shouldFindProductBySkuUsingJpa() {
        Product product = jpaProductRepository.findBySku("LAP-001");

        assertEquals("LAP-001", product.getSku());
        assertEquals("Laptop", product.getName());
        assertEquals(10, product.getQuantity());
    }

    @Test
    void shouldSaveProductUsingJpa() {
        Product product = new Product("TAB-001", "Tablet", 8);

        entityManager.getTransaction().begin();
        jpaProductRepository.save(product);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product savedProduct = jpaProductRepository.findBySku("TAB-001");
        assertEquals("TAB-001", savedProduct.getSku());
        assertEquals("Tablet", savedProduct.getName());
        assertEquals(8, savedProduct.getQuantity());
    }

    @Test
    void shouldPersistAndQueryStockMovementsByProductSku() {
        Product product = jpaProductRepository.findBySku("LAP-001");
        StockMovement stockMovement = new StockMovement(
                StockMovementType.IN,
                5,
                LocalDateTime.now(),
                product
        );
        product.addStockMovement(stockMovement);

        entityManager.getTransaction().begin();
        entityManager.persist(stockMovement);
        entityManager.getTransaction().commit();
        entityManager.clear();

        List<StockMovement> stockMovements = entityManager
                .createQuery(
                        "SELECT m FROM StockMovement m WHERE m.product.sku = :sku ORDER BY m.createdAt",
                        StockMovement.class
                )
                .setParameter("sku", "LAP-001")
                .getResultList();

        assertEquals(1, stockMovements.size());
        assertEquals(StockMovementType.IN, stockMovements.getFirst().getType());
        assertEquals(5, stockMovements.getFirst().getQuantity());
        assertEquals("LAP-001", stockMovements.getFirst().getProduct().getSku());
    }

    @Test
    void shouldLoadStockMovementsLazilyWhileEntityManagerIsOpen() {
        Product product = jpaProductRepository.findBySku("LAP-001");

        entityManager.getTransaction().begin();
        StockMovement stockMovement = new StockMovement(
                StockMovementType.OUT,
                2,
                LocalDateTime.now(),
                product
        );
        product.addStockMovement(stockMovement);
        entityManager.persist(stockMovement);
        entityManager.getTransaction().commit();
        entityManager.clear();

        Product reloadedProduct = jpaProductRepository.findBySku("LAP-001");
        List<StockMovement> stockMovements = reloadedProduct.getStockMovements();

        assertFalse(stockMovements.isEmpty());
        assertEquals(2, stockMovements.getLast().getQuantity());
    }
}
