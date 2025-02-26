import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AddEventTest {
    // Using ByteArrayOutputStream to capture test output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    // Setup to be executed before each test.
    @Before
    public void setUp() {
        // Redirect standard output and error output to our streams.
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    // Cleanup to be executed after each test.
    @After
    public void tearDown() {
        // Reset System.in, System.out, and System.err to their original streams.
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // Positive test case for the addEvent method.
    @Test
    public void testAddEvent_Positive() {
        // Set input for positive test case.
        String input = "ayse\n12:00\nTest Location\nTest Category\n2024-05-01\n10\n50\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Call the method to be tested.
        EventManager eventManager = new EventManager();
        eventManager.addEvent();

        // Validate the output.
        assertTrue(outContent.toString().contains("Manager added event successfully."));

        // Reset System.in.
        System.setIn(originalIn);
    }

    // Negative test case for the addEvent method.
    @Test
    public void testAddEvent_Negative() {
        // Set input for negative test case.
        String input = "1\n1\n1\n1\n1\n1\n1\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Capture console output for comparison.
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        // Call the method to be tested.
        EventManager eventManager = new EventManager();
        eventManager.addEvent();

        // Validate the output.
        assertFalse(outContent.toString().contains("Manager added event successfully."));

        // Reset System.in.
        System.setIn(originalIn);
    }
}
