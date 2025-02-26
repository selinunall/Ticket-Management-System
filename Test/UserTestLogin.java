import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UserTestLogin {

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
    /**
     * Test case to verify successful user login.
     * This test ensures that the system successfully logs in a user
     * when provided with correct email and password.
     */
    @Test
    public void testLoginAsUser_Successful() {
        // Prepare test data
        String testEmail = "ayem123";
        String testPassword = "ayem123";

        // Mock user input
        String userInput = testEmail + "\n" + testPassword + "\n";
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // Call the method to be tested
        assertTrue(EventManagementSystem.loginAsUser());

    }
    /**
     * Test case to verify unsuccessful user login.
     * This test ensures that the system handles incorrect email
     * or password gracefully and does not allow login.
     */
    @Test
    public void testLoginAsUser_Unsuccessful() {
        // Prepare test data
        String testEmail = "nonexistent@example.com";
        String testPassword = "invalidpassword";

        // Mock user input
        String userInput = testEmail + "\n" + testPassword + "\n";
        InputStream in = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(in);

        // Call the method to be tested
        assertFalse(EventManagementSystem.loginAsUser());

    }
}
