package org.example.smartstock.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public class EntityManagerFactoryProvider {
    private static final String PERSISTENCE_UNIT_NAME = "smartstock-pu";

    public EntityManagerFactory create() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public EntityManagerFactory create(Map<String, Object> properties) {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
    }
}
