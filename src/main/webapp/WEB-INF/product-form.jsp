<%--
  Created by IntelliJ IDEA.
  User: hamza
  Date: 2/4/2026
  Time: 00:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form method="post" action="/products/new">
        <input type="text" name="sku">
        <input type="text" name="name">
        <input type="number" name="quantity">
        <button type="submit">Create</button>
    </form>
</body>
</html>
