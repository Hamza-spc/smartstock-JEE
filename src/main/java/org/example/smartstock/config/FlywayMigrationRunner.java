package org.example.smartstock.config;

import org.flywaydb.core.Flyway;

public class FlywayMigrationRunner {
    private final ConnectionFactory connectionFactory;

    public FlywayMigrationRunner(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        connectionFactory.getUrl(),
                        connectionFactory.getUsername(),
                        connectionFactory.getPassword()
                )
                .load();
        flyway.migrate();
    }
}
