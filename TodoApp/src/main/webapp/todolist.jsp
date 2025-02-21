<%--
  Created by IntelliJ IDEA.
  User: mattd
  Date: 2025-02-20
  Time: 9:53 p.m.
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.example.todoapp.model.Todo" %>
<%@ page import="java.util.List" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Todo List</title>
</head>
<body>
<h1>Todo List</h1>
<a href="todos?action=new">Add New Todo</a>
    <table border="1">
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Completed</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="todo" items="${todos}">
            <tr>
                <td>${todo.title}</td>
                <td>${todo.description}</td>
                <td>${todo.completed ? 'Yes' : 'No'}</td>
                <td>
                    <a href="todos?action=edit&id=${todo.id}">Edit</a>
                    <form action="todos" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${todo.id}">
                        <input type="submit" value="Delete" onclick="return confirm('Are you sure?')">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
<p>Total Todos: ${fn:length(todos)}</p>
</body>
</html>
