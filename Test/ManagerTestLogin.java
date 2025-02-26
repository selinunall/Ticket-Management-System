import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

public class ManagerTestLogin {

    private InputStream originalSystemIn;

    @Before
    public void setUp() {
        // Save the original System.in stream
        originalSystemIn = System.in;
    }

    @After
    public void tearDown() {
        // Restore the System.in stream
        System.setIn(originalSystemIn);
    }

    @Test
    public void testLoginAsEventManager_Successful() throws SQLException {
        // Prepare test data
        int testManagerId = 1234567;
        String testPassword = "ayse2002";

        // Mock user input
        String userInput = testManagerId + "\n" + testPassword + "\n";
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // Call the method to be tested
        assertTrue(EventManagementSystem.loginAsEventManager());

        // Verify console output
        // Add assertions or verification code here if needed

    }

    @Test
    public void testLoginAsEventManager_Unsuccessful() throws SQLException {
        // Prepare test data
        int testManagerId = 99999; // Assuming this manager ID does not exist
        String testPassword = "invalidpassword";

        // Mock user input
        String userInput = testManagerId + "\n" + testPassword + "\n";
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // Call the method to be tested
        assertFalse(EventManagementSystem.loginAsEventManager());

        // Verify console output
        // Add assertions or verification code here if needed
    }
    // This test class validates the login process for event managers in the EventManagementSystem class.
    // It includes both successful and unsuccessful login scenarios to ensure correct authentication behavior.

}

