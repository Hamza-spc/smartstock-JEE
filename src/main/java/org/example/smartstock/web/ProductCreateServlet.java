package org.example.smartstock.web;

import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.example.smartstock.service.InventoryService;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@WebServlet("/products/new")
public class ProductCreateServlet extends HttpServlet {
    @Inject
    private InventoryService inventoryService;

    @Inject
    private EntityManager entityManager;

    @Inject
    private Validator validator;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/product-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sku = request.getParameter("sku");
        String name = request.getParameter("name");
        Integer quantity = parseQuantity(request.getParameter("quantity"));
        ProductForm productForm = new ProductForm(sku, name, quantity);

        Map<String, String> validationErrors = validate(productForm);
        if (!validationErrors.isEmpty()) {
            request.setAttribute("errors", validationErrors);
            request.setAttribute("form", productForm);
            request.getRequestDispatcher("/WEB-INF/product-form.jsp").forward(request, response);
            return;
        }

        try {
            entityManager.getTransaction().begin();
            inventoryService.registerProduct(productForm.getSku(), productForm.getName(), productForm.getQuantity());
            entityManager.getTransaction().commit();

            request.getSession().setAttribute("flashMessage", "Product " + productForm.getSku() + " created successfully.");
            response.sendRedirect(request.getContextPath() + "/products");
        } catch (RuntimeException e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        }
    }

    private Integer parseQuantity(String rawQuantity) {
        try {
            return Integer.valueOf(rawQuantity);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Map<String, String> validate(ProductForm productForm) {
        Set<ConstraintViolation<ProductForm>> violations = validator.validate(productForm);
        Map<String, String> errors = new LinkedHashMap<>();
        for (ConstraintViolation<ProductForm> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            errors.putIfAbsent(fieldName, violation.getMessage());
        }
        return errors;
    }
}
