import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    private Connection connection;

    @Before
    public void setUp() {
        // Setup işlemi, pozitif test için kullanılacaktır.
    }

    @After
    public void tearDown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDatabaseConnectionPositive() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventDeeneme1", "root", "Blabla2002_");
            assertNotNull("Connection should not be null", connection);
        } catch (SQLException e) {
            fail("Should have connected to the database");
        }
    }

    @Test
    public void testDatabaseConnection_Negative() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/invalidDatabase", "root", "Blabla2002_");
            fail("Should not have connected to the database");
        } catch (SQLException e) {
            assertTrue("Expected to catch SQLException", true);
        }
    }
}