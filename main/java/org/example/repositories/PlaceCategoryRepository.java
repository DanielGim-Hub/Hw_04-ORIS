package org.example.repositories;

import org.example.models.PlaceCategory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceCategoryRepository {
    private final Connection connection;

    public PlaceCategoryRepository(Connection connection) {
        this.connection = connection;
    }

    // Найти категорию по ID
    public PlaceCategory findById(int id) throws SQLException {
        String query = "SELECT * FROM PlaceCategory WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new PlaceCategory(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }
        }
        return null;
    }

    // Получить список всех категорий
    public List<PlaceCategory> findAll() throws SQLException {
        List<PlaceCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM PlaceCategory";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                categories.add(new PlaceCategory(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        }
        return categories;
    }

    // Сохранить новую категорию
    public void save(PlaceCategory category) throws SQLException {
        String query = "INSERT INTO PlaceCategory (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.executeUpdate();
        }
    }

    // Обновить категорию
    public void update(PlaceCategory category) throws SQLException {
        String query = "UPDATE PlaceCategory SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category.getName());
            stmt.setInt(2, category.getId());
            stmt.executeUpdate();
        }
    }

    // Удалить категорию по ID
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM PlaceCategory WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
