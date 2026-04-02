package org.example.smartstock;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductRepository productRepository = new InMemoryProductRepository();
        InventoryService inventoryService = new InventoryService(productRepository);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("inventoryService", inventoryService);
    }
}
