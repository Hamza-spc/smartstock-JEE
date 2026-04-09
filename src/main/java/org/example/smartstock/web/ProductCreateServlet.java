package org.example.smartstock.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.smartstock.service.InventoryService;

import java.io.IOException;

@WebServlet("/products/new")
public class ProductCreateServlet extends HttpServlet {
    private InventoryService inventoryService;

    @Override
    public void init() {
        inventoryService = (InventoryService) getServletContext().getAttribute("inventoryService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/product-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sku = request.getParameter("sku");
        String name = request.getParameter("name");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        inventoryService.registerProduct(sku, name, quantity);
        request.getSession().setAttribute("flashMessage", "Product " + sku + " created successfully.");
        response.sendRedirect(request.getContextPath() + "/products");
    }
}
