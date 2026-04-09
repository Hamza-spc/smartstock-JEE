package org.example.smartstock.web;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.smartstock.repository.JpaProductRepository;
import org.example.smartstock.service.InventoryService;

import java.io.IOException;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManagerFactory entityManagerFactory =
                (EntityManagerFactory) getServletContext().getAttribute("entityManagerFactory");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object flashMessage = session.getAttribute("flashMessage");
            if (flashMessage != null) {
                request.setAttribute("flashMessage", flashMessage);
                session.removeAttribute("flashMessage");
            }
        }

        try {
            InventoryService inventoryService = new InventoryService(new JpaProductRepository(entityManager));
            request.setAttribute("products", inventoryService.getAllProducts());
            request.getRequestDispatcher("/WEB-INF/product-list.jsp").forward(request, response);
        } finally {
            entityManager.close();
        }
    }
}
