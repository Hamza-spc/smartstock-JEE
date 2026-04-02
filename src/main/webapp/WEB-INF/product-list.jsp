<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="org.example.smartstock.Product" %>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<h1>Product List</h1>

<a href="<%= request.getContextPath() %>/products/new">Create Product</a>

    <% if (products == null || products.isEmpty()) { %>
<p>No products found.</p>
    <% } else { %>
<table border="1">
    <thead>
    <tr>
        <th>SKU</th>
        <th>Name</th>
        <th>Quantity</th>
    </tr>
    </thead>
    <tbody>
        <% for (Product product : products) { %>
    <tr>
        <td><%= product.getSku() %></td>
        <td><%= product.getName() %></td>
        <td><%= product.getQuantity() %></td>
    </tr>
        <% } %>
    </tbody>
</table>
<% } %>
</body>
</html>