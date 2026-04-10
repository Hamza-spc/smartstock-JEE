<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Product</title>
</head>
<body>
<h1>Create Product</h1>

<c:if test="${not empty errors}">
    <p>Please fix the validation errors below.</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/products/new">
    <div>
        <label for="sku">SKU</label>
        <input type="text" id="sku" name="sku" required value="${form.sku}">
        <c:if test="${not empty errors.sku}">
            <span>${errors.sku}</span>
        </c:if>
    </div>

    <div>
        <label for="name">Name</label>
        <input type="text" id="name" name="name" required value="${form.name}">
        <c:if test="${not empty errors.name}">
            <span>${errors.name}</span>
        </c:if>
    </div>

    <div>
        <label for="quantity">Initial Quantity</label>
        <input type="number" id="quantity" name="quantity" min="0" required value="${form.quantity}">
        <c:if test="${not empty errors.quantity}">
            <span>${errors.quantity}</span>
        </c:if>
    </div>

    <button type="submit">Create</button>
</form>
</body>
</html>
