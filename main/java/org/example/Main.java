package org.example;

import org.example.models.User;
import org.example.repositories.UserRepository;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "qwe123";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws SQLException {
        // Establish the database connection
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            UserRepository userRepository = new UserRepository(connection);
            Scanner scanner = new Scanner(System.in);

            System.out.println("Choose operation: 1 - Add user, 2 - View all users, 3 - Find user by ID, 4 - Update user, 5 - Delete user");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the leftover newline

            switch (choice) {
                case 1:
                    // Add a new user
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter email:");
                    String email = scanner.nextLine();
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    Timestamp createdAt = new Timestamp(System.currentTimeMillis());

                    User newUser = new User(0, username, email, password, createdAt);
                    userRepository.save(newUser);
                    System.out.println("User added successfully!");
                    break;

                case 2:
                    // View all users
                    List<User> users = userRepository.findAll();
                    for (User user : users) {
                        System.out.println(user);
                    }
                    break;

                case 3:
                    // Find user by ID
                    System.out.println("Enter user ID:");
                    int userId = scanner.nextInt();
                    User foundUser = userRepository.findById(userId);
                    if (foundUser != null) {
                        System.out.println("User found: " + foundUser);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;

                case 4:
                    // Update user
                    System.out.println("Enter user ID to update:");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.println("Enter new username:");
                    String newUsername = scanner.nextLine();
                    System.out.println("Enter new email:");
                    String newEmail = scanner.nextLine();
                    System.out.println("Enter new password:");
                    String newPassword = scanner.nextLine();

                    User updatedUser = new User(updateId, newUsername, newEmail, newPassword, new Timestamp(System.currentTimeMillis()));
                    userRepository.update(updatedUser);
                    System.out.println("User updated successfully!");
                    break;

                case 5:
                    // Delete user
                    System.out.println("Enter user ID to delete:");
                    int deleteId = scanner.nextInt();
                    userRepository.delete(deleteId);
                    System.out.println("User deleted successfully!");
                    break;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }
}
