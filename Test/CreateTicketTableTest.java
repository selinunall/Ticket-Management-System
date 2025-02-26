import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateTicketTableTest {

    private Connection connection;
    private Statement statement;

    // Setup method to be executed before each test
    @Before
    public void setUp() throws Exception {
        // Database connection details
        String dbUrl = "jdbc:mysql://localhost:3306/eventDeeneme1";
        String user = "root";
        String pass = "Blabla2002_";
        // Establishing connection to the database
        connection = DriverManager.getConnection(dbUrl, user, pass);
        // Creating a statement object to execute SQL queries
        statement = connection.createStatement();
    }

    // Teardown method to be executed after each test
    @After
    public void tearDown() throws Exception {
        // Closing the statement if it is not null
        if (statement != null) {
            statement.close();
        }
        // Closing the connection if it is not null
        if (connection != null) {
            connection.close();
        }
    }

    // Positive test case to create the ticket table
    @Test
    public void testCreateTicketTablePositive() throws SQLException {
        // SQL query to create the ticket table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ticket ("
                + "ticketId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                + "ticketPrice DOUBLE NOT NULL,"
                + "eventId INT NOT NULL,"
                + "userEmail VARCHAR(255)"
                + ")";
        // Executing the SQL query to create the table
        statement.executeUpdate(createTableSQL);
        System.out.println("Ticket table created successfully.");
        // Asserting that the table was created successfully
        assertTrue(tableExists("ticket"));
    }

    // Negative test case to handle table creation failure
    @Test
    public void testCreateTicketTableNegative() throws SQLException {
        // SQL query to create the ticket table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ticket ("
                + "ticketId INT AUTO_INCREMENT PRIMARY KEY NOT NULL,"
                + "ticketPrice DOUBLE NOT NULL,"
                + "eventId INT NOT NULL,"
                + "userEmail VARCHAR(255)"
                + ")";
        // Executing the SQL query to create the table
        statement.executeUpdate(createTableSQL);
        System.out.println("Failed to create ticket table.");
        // Asserting that the table was not created (which should fail)
        assertFalse(!tableExists("ticket"));
    }

    // Helper method to check if a table exists in the database
    private boolean tableExists(String tableName) throws SQLException {
        String query = "SHOW TABLES LIKE '" + tableName + "'";
        // Executing the query and checking if the result set is not empty
        return statement.executeQuery(query).next();
    }
}
