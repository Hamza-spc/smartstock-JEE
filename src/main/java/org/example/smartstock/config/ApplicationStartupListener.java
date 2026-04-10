package org.example.smartstock.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        FlywayMigrationRunner flywayMigrationRunner = new FlywayMigrationRunner(connectionFactory);
        flywayMigrationRunner.migrate();
    }
}
