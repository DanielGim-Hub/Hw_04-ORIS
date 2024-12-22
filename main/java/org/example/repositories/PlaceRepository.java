package org.example.repositories;

import org.example.models.Place;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceRepository {
    private final Connection connection;

    public PlaceRepository(Connection connection) {
        this.connection = connection;
    }

    public Place findById(int id) throws SQLException {
        String query = "SELECT * FROM Place WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Place(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id")
                );
            }
        }
        return null;
    }


    public List<Place> findAll() throws SQLException {
        List<Place> places = new ArrayList<>();
        String query = "SELECT * FROM Place";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                places.add(new Place(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id")
                ));
            }
        }
        return places;
    }


    public void save(Place place) throws SQLException {
        String query = "INSERT INTO Place (name, description, category_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, place.getName());
            stmt.setString(2, place.getDescription());
            stmt.setInt(3, place.getCategoryId());
            stmt.executeUpdate();
        }
    }

    public void update(Place place) throws SQLException {
        String query = "UPDATE Place SET name = ?, description = ?, category_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, place.getName());
            stmt.setString(2, place.getDescription());
            stmt.setInt(3, place.getCategoryId());
            stmt.setInt(4, place.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Place WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}