package org.example.smartstock.web;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.smartstock.repository.JpaProductRepository;
import org.example.smartstock.service.InventoryService;

import java.io.IOException;

@WebServlet("/products/new")
public class ProductCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/product-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sku = request.getParameter("sku");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        EntityManagerFactory entityManagerFactory =
                (EntityManagerFactory) getServletContext().getAttribute("entityManagerFactory");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            InventoryService inventoryService = new InventoryService(new JpaProductRepository(entityManager));
            entityManager.getTransaction().begin();
            inventoryService.registerProduct(sku, name, quantity);
            entityManager.getTransaction().commit();

            request.getSession().setAttribute("flashMessage", "Product " + sku + " created successfully.");
            response.sendRedirect(request.getContextPath() + "/products");
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
