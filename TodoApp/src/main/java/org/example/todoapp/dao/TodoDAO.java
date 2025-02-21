package org.example.todoapp.dao;

import org.example.todoapp.DatabaseUtil;
import org.example.todoapp.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todos";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setTitle(rs.getString("title"));
                todo.setDescription(rs.getString("description"));
                todo.setCompleted(rs.getBoolean("is_completed"));
                todos.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public void addTodo(Todo todo) {
        String sql = "INSERT INTO todos (title, description, is_completed) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, todo.getTitle());
            pstmt.setString(2, todo.getDescription());
            pstmt.setBoolean(3, todo.isCompleted());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTodo(Todo todo) {
        String sql = "UPDATE todos SET title = ?, description = ?, is_completed = ? WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, todo.getTitle());
            pstmt.setString(2, todo.getDescription());
            pstmt.setBoolean(3, todo.isCompleted());
            pstmt.setInt(4, todo.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTodo(int id) {

        String sql = "DELETE FROM todos WHERE id = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Todo getTodoById(int id) {
        String sql = "SELECT * FROM todos WHERE id = ?";
        Todo todo = null;
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                todo = new Todo();
                todo.setId(rs.getInt("id"));
                todo.setTitle(rs.getString("title"));
                todo.setDescription(rs.getString("description"));
                todo.setCompleted(rs.getBoolean("is_completed"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todo;
    }
}
