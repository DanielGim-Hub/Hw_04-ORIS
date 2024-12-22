package org.example.repositories;

import org.example.models.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository {
    private final Connection connection;

    public RouteRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Route> findRoutesByUserId(int userId) throws SQLException {
        List<Route> routes = new ArrayList<>();
        String query = "SELECT * FROM Route WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Route route = new Route(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("user_id"),
                            rs.getTimestamp("created_at")
                    );
                    routes.add(route);
                }
            }
        }
        return routes;
    }

    public void addPlaceToRoute(int routeId, int placeId) throws SQLException {
        String query = "INSERT INTO Route_Place (route_id, place_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, routeId);
            stmt.setInt(2, placeId);
            stmt.executeUpdate();
        }
    }

    public void removePlaceFromRoute(int routeId, int placeId) throws SQLException {
        String query = "DELETE FROM Route_Place WHERE route_id = ? AND place_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, routeId);
            stmt.setInt(2, placeId);
            stmt.executeUpdate();
        }
    }

    public Route findById(int id) throws SQLException {
        String query = "SELECT * FROM Route WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Route(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getInt("user_id"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }

    public void save(Route route) throws SQLException {
        String query = "INSERT INTO Route (name, description, user_id, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, route.getName());
            stmt.setString(2, route.getDescription());
            stmt.setInt(3, route.getUserId());
            stmt.setTimestamp(4, route.getCreatedAt());
            stmt.executeUpdate();
        }
    }

    public void update(Route route) throws SQLException {
        String query = "UPDATE Route SET name = ?, description = ?, user_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, route.getName());
            stmt.setString(2, route.getDescription());
            stmt.setInt(3, route.getUserId());
            stmt.setInt(4, route.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Route WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
