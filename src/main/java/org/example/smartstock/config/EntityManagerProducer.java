package org.example.smartstock.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@ApplicationScoped
public class EntityManagerProducer {
    private EntityManagerFactory entityManagerFactory;

    @PostConstruct
    void init() {
        entityManagerFactory = new EntityManagerFactoryProvider().create();
    }

    @Produces
    @ApplicationScoped
    public EntityManagerFactory produceEntityManagerFactory() {
        return entityManagerFactory;
    }

    @Produces
    @RequestScoped
    public EntityManager produceEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void closeEntityManager(@jakarta.enterprise.inject.Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @PreDestroy
    void destroy() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
