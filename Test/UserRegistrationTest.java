
import static org.junit.Assert.*;
import org.junit.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * Unit tests for the user registration functionality in the EventManagementSystem.
 */
public class UserRegistrationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private InputStream standardInputStream;
    private EventManagementSystem system;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        // Save standard input stream and initialize the system
        standardInputStream = System.in;
        system = new EventManagementSystem();
    }

    @After
    public void tearDown() {
        System.setIn(standardInputStream);
    }
    /**
     * Test case to verify user registration with valid input.
     * This test ensures that the system successfully registers a user
     * when provided with valid input data.
     */
    @Test
    public void testRegisterAsUser_Positive() {
        // Prepare input for the test
        String simulatedInput = "Jjdfkj\nDoe\n123456\njohbjhn@example.com\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Run the method to be tested
        system.registerAsUser();

        // Write assertions to verify the result
        assertTrue(outContent.toString().contains("User registered successfully."));

    }

    @Test
    public void testRegisterAsUser_Negative() {
        // Prepare input for the test
        String simulatedInput = "1\n1\n1\n1\n"; // Invalid email format
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Run the method to be tested
        system.registerAsUser();

        // Write assertions to verify the result
        assertTrue(outContent.toString().contains("User with this email already exists."));

    }
}