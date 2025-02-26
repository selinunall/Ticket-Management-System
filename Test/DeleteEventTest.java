import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class DeleteEventTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testDeleteEvent_PositiveScenario() {
        // Prepare test data
        ByteArrayInputStream in = new ByteArrayInputStream("9\n".getBytes()); // Assuming event ID 1 exists
        System.setIn(in);

        // Call the method to be tested
        EventManager manager = new EventManager();
        manager.deleteEvent();

        // Validate the output
        assertTrue(outContent.toString().contains("Event deleted successfully."));

        // Reset System.in
        System.setIn(originalIn);
    }


    @Test
    public void testDeleteEvent_NegativeScenario() {
        // Prepare test data
        ByteArrayInputStream in = new ByteArrayInputStream("999\n".getBytes()); // Assuming event ID 999 does not exist
        System.setIn(in);

        // Perform the test
        EventManager manager = new EventManager();
        manager.deleteEvent();

        // Check the output
        assertTrue(outContent.toString().contains("No event found with the given ID."));
    }
}
