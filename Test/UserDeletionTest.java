import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDeletionTest {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventsystem";
    private static final String USER = "root";
    private static final String PASS = "Blabla2002_";

    // InputStream mocks
    private InputStream systemIn;
    private ByteArrayInputStream testIn;

    @Before
    public void setUp() {
        // Mock user input for testing
        String input = "john@example.com"; // Assuming this email exists in your test database
        testIn = new ByteArrayInputStream(input.getBytes());
        systemIn = System.in;
        System.setIn(testIn);
    }

    @After
    public void tearDown() {
        // Restore System.in
        System.setIn(systemIn);
    }

    @Test
    public void testDeleteUser_validUser() {
        // Positive scenario testing for user deletion
        EventManager userDAO = new EventManager();

        userDAO.deleteUser(); // Delete user

        // If the user has been deleted, there should be no user with this email address
        assertFalse(checkUserExists("john@example.com"));
        System.out.println("Deletion is successful.");
    }

    @Test
    public void testDeleteUser_invalidUser() {
        EventManager userDAO = new EventManager();

        // Redirect System.out for testing
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userDAO.deleteUser(); // Try to delete the user

        // Check if the expected error message is printed
        assertTrue(outContent.toString().contains("Failed to delete user. User not found."));
        System.out.println("Failed to delete user. User not found.");

        // Restore System.out
        System.setOut(System.out);
    }

    // Helper method to check if a user with a specific email address exists in the database
    private boolean checkUserExists(String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String query = "SELECT * FROM user WHERE userEmail = '" + email + "'";
            Statement statement = conn.createStatement();
            return statement.executeQuery(query).next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
