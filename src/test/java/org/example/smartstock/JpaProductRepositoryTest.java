package org.example.smartstock;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.smartstock.config.ConnectionFactory;
import org.example.smartstock.config.DatabaseInitializer;
import org.example.smartstock.config.EntityManagerFactoryProvider;
import org.example.smartstock.domain.Product;
import org.example.smartstock.repository.JpaProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JpaProductRepositoryTest {
    private static final String TEST_DB_URL = "jdbc:h2:mem:smartstock-jpa-test;DB_CLOSE_DELAY=-1;MODE=LEGACY";

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private JpaProductRepository jpaProductRepository;

    @BeforeEach
    void setUp() {
        ConnectionFactory connectionFactory = new ConnectionFactory(TEST_DB_URL);
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionFactory);
        databaseInitializer.initializeSchema();

        EntityManagerFactoryProvider provider = new EntityManagerFactoryProvider();
        entityManagerFactory = provider.create(Map.of(
                "jakarta.persistence.jdbc.url", TEST_DB_URL,
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
}
