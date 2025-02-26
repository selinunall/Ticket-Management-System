import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventManagerRegistrationTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDeeneme1";
    private static final String USER = "root";
    private static final String PASS = "Blabla2002_";

    @BeforeEach
    public void setUp() {
        System.out.println("Test ortamı ayarlanıyor...");
        EventManagementSystem.createEventManagerDatabase();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test ortamı temizleniyor...");
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM eventManager");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValidEmailForEventManagerRegistration() {
        String validEmail = "manager@example.com";
        assertTrue(EventManagementSystem.isValidEmail(validEmail), "E-posta geçerli olmalı");
    }

    @Test
    public void testInvalidEmailForEventManagerRegistration() {
        String invalidEmail = "managerexample.com";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "E-posta geçersiz olmalı");

        invalidEmail = "manager@.com";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "E-posta geçersiz olmalı");

        invalidEmail = "manager@com";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "E-posta geçersiz olmalı");

        invalidEmail = "manager@com.";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "E-posta geçersiz olmalı");
    }
}
