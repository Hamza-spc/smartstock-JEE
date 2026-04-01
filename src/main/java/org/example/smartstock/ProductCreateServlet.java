package org.example.smartstock;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/products/new")
public class ProductCreateServlet extends HttpServlet {
    private InventoryService inventoryService;

    @Override
    public void init() {
        ProductRepository repository = new InMemoryProductRepository();
        inventoryService = new InventoryService(repository);
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
        Product product = inventoryService.getProduct(sku);

        request.setAttribute("product", product);
        request.getRequestDispatcher("/WEB-INF/product-created.jsp").forward(request, response);
    }
}
