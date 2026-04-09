package org.example.smartstock.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        FlywayMigrationRunner flywayMigrationRunner = new FlywayMigrationRunner(connectionFactory);
        flywayMigrationRunner.migrate();
        entityManagerFactory = new EntityManagerFactoryProvider().create();

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("entityManagerFactory", entityManagerFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
