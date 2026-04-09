<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.example.smartstock.domain.Product" %>
<%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Product Created</title>
</head>
<body>
<h1>Product Created Successfully</h1>

<p>SKU: <%= product.getSku() %></p>
<p>Name: <%= product.getName() %></p>
<p>Quantity: <%= product.getQuantity() %></p>

<a href="<%= request.getContextPath() %>/products/new">Create another product</a>
</body>
</html>