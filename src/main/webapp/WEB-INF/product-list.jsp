<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<h1>Product List</h1>

<a href="${pageContext.request.contextPath}/products/new">Create Product</a>

<c:if test="${not empty flashMessage}">
<p>${flashMessage}</p>
</c:if>

<c:if test="${empty products}">
<p>No products found.</p>
</c:if>

<c:if test="${not empty products}">
<table border="1">
    <thead>
    <tr>
        <th>SKU</th>
        <th>Name</th>
        <th>Quantity</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="product" items="${products}">
    <tr>
        <td>${product.sku}</td>
        <td>${product.name}</td>
        <td>${product.quantity}</td>
    </tr>
    </c:forEach>
    </tbody>
</table>
</c:if>
</body>
</html>
