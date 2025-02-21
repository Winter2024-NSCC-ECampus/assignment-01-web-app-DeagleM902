<%--
  Created by IntelliJ IDEA.
  User: mattd
  Date: 2025-02-20
  Time: 9:53 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:choose>
        <c:when test="${not empty todo}">Edit Todo</c:when>
        <c:otherwise>Add New Todo</c:otherwise>
    </c:choose>
    </title>
</head>
<body>
<h1>
    <c:choose>
        <c:when test="${not empty todo}">Edit Todo</c:when>
        <c:otherwise>Add New Todo</c:otherwise>
    </c:choose>
</h1>
<form action="todos" method="post">
    <!-- If editing, include hidden id and action "update"; otherwise, use action "add" -->
    <c:if test="${not empty todo}">
        <input type="hidden" name="id" value="${todo.id}" />
        <input type="hidden" name="action" value="update" />
    </c:if>
    <c:if test="${empty todo}">
        <input type="hidden" name="action" value="add" />
    </c:if>
    <label>Title:</label>
    <input type="text" name="title" value="${todo.title}" required /><br/>
    <label>Description:</label>
    <textarea name="description" required>${todo.description}</textarea><br/>
    <label>Completed:</label>
    <input type="checkbox" name="is_completed" value="true"
           <c:if test="${todo.completed}">checked</c:if> /><br/>
    <input type="submit" value="Submit" />
</form>
<a href="todos">Return to Todo List</a>
</body>
</html>