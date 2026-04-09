package org.example.smartstock.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.smartstock.service.InventoryService;

import java.io.IOException;

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {

    private InventoryService inventoryService;

    @Override
    public void init() {
        inventoryService = (InventoryService) getServletContext().getAttribute("inventoryService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object flashMessage = session.getAttribute("flashMessage");
            if (flashMessage != null) {
                request.setAttribute("flashMessage", flashMessage);
                session.removeAttribute("flashMessage");
            }
        }

        request.setAttribute("products", inventoryService.getAllProducts());
        request.getRequestDispatcher("/WEB-INF/product-list.jsp").forward(request, response);
    }
}
