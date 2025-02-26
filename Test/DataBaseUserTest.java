import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class DataBaseUserTest {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/eventDeeneme1";
    private static final String USER = "root";
    private static final String INCORRECT_USER = "root34";
    private static final String PASS = "Blabla2002_";
    private Connection conn; // Class-level connection for demonstration

    @Before
    public void setUp() throws ClassNotFoundException {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = null; // Initialize the connection
    }

    @After
    public void tearDown() {
        // Close the connection if it was opened
        if (conn != null) {
            try {
                conn.close();
                System.out.println("TearDown: Connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testConnectionWithCorrectPassword() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            assertNotNull(conn);
        } catch (SQLException e) {
            fail("Connection should not fail with correct password. Exception: " + e.getMessage());
        }
    }

    @Test
    public void testConnectionWithIncorrectPassword() {
        SQLException exception = assertThrows(SQLException.class, () -> {
            try {
                conn = DriverManager.getConnection(DB_URL, INCORRECT_USER, PASS);
            } finally {
                // Close the connection if it was opened
                if (conn != null) {
                    conn.close();
                }
            }
        });
        assertTrue(exception.getMessage().contains("Access denied"));
    }
}
