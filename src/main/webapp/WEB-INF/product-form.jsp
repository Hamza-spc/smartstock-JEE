<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Product</title>
</head>
<body>
<h1>Create Product</h1>

<form method="post" action="${pageContext.request.contextPath}/products/new">
    <div>
        <label for="sku">SKU</label>
        <input type="text" id="sku" name="sku" required>
    </div>

    <div>
        <label for="name">Name</label>
        <input type="text" id="name" name="name" required>
    </div>

    <div>
        <label for="quantity">Initial Quantity</label>
        <input type="number" id="quantity" name="quantity" min="0" required>
    </div>

    <button type="submit">Create</button>
</form>
</body>
</html>