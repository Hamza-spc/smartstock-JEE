package org.example.smartstock;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(connectionFactory);
        databaseInitializer.initializeSchema();

        ProductRepository productRepository = new JdbcProductRepository(connectionFactory);
        InventoryService inventoryService = new InventoryService(productRepository);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("inventoryService", inventoryService);
    }
}
