package org.example.todoapp.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.todoapp.dao.TodoDAO;
import org.example.todoapp.model.Todo;

import java.io.IOException;

@WebServlet(name = "TodoServlet", value = "/todos")
public class TodoServlet extends HttpServlet {
    private TodoDAO todoDAO;

    @Override
    public void init() {
        todoDAO = new TodoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteTodo(request, response);
                    break;
                default:
                    listTodos(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "add":
                    addTodo(request, response);
                    break;
                case "update":
                    updateTodo(request, response);
                    break;
                case "delete":
                    deleteTodo(request, response);
                    break;
                default:
                    listTodos(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listTodos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("todos", todoDAO.getAllTodos());
        RequestDispatcher dispatcher = request.getRequestDispatcher("todolist.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("todoform.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Todo existingTodo = todoDAO.getTodoById(id);
        request.setAttribute("todo", existingTodo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("todoform.jsp");
        dispatcher.forward(request, response);
    }

    private void addTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean isCompleted = Boolean.parseBoolean(request.getParameter("is_completed"));
        Todo newTodo = new Todo();

        newTodo.setTitle(title);
        newTodo.setDescription(description);
        newTodo.setCompleted(isCompleted);

        todoDAO.addTodo(newTodo);
        response.sendRedirect("todos");
    }

    private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        boolean isCompleted = Boolean.parseBoolean(request.getParameter("is_completed"));
        Todo todo = new Todo();

        todo.setId(id);
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setCompleted(isCompleted);

        todoDAO.updateTodo(todo);
        response.sendRedirect("todos");
    }

    private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        todoDAO.deleteTodo(id);
        response.sendRedirect("todos");
    }

}
