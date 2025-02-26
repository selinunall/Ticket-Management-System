import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabaseTest {
    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDeeneme1";
    private static final String USER = "root";
    private static final String PASS = "Blabla2002_";

    // Test method to create a database with correct credentials
    @Test
    public static void testCreateDatabasePositive() {
        String sql = "CREATE DATABASE IF NOT EXISTS eventsystem";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "Blabla2002_");
             Statement stmt = conn.createStatement()) {
            // Execute SQL statement to create database
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Test method to create a database with incorrect credentials
    @Test
    public static void testCreateDatabaseNegative() {
        String sql = "CREATE DATABASE IF NOT EXISTS eventsystem";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "wrong_user", "wrong_password");
             Statement stmt = conn.createStatement()) {
            // Attempt to execute SQL statement with incorrect credentials
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            // Catch SQL exception and print an error message
            System.out.println("Failed to create database. Error: " + e.getMessage());
        }
    }

    // Main method to run the tests
    public static void main(String[] args) {
        testCreateDatabasePositive();
        testCreateDatabaseNegative();
    }
}
