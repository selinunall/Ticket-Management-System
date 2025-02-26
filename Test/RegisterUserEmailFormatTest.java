import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterUserEmailFormatTest {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDeeneme1";
    private static final String USER = "root";
    private static final String PASS = "Blabla2002_";

    @BeforeEach
    public void setUp() {
        System.out.println("Setting the testing area...");
        EventManagementSystem.createUserDatabase();
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Cleaning the testing area...");
        try (Connection conn = DriverManager.getConnection(DB_URL, USER,PASS);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValidEmailForUserRegistration() {
        String validEmail = "user@example.com";
        assertTrue(EventManagementSystem.isValidEmail(validEmail), "Email must be valid...");
    }

    @Test
    public void testInvalidEmailForUserRegistration() {
        String invalidEmail = "userexample.com";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "Email must be valid...");

        invalidEmail = "user@.com";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "Email must be valid...");

        invalidEmail = "user@com";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "Email must be valid...");

        invalidEmail = "user@com.";
        assertFalse(EventManagementSystem.isValidEmail(invalidEmail), "Email must be valid...");
    }
}
