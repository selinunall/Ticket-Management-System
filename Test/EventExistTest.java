import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
public class EventExistTest {
    private InputStream inputStream ;
    private ByteArrayOutputStream outContent;
    private RegularUser user;
    private  PrintStream originalOut;
    @Before
    public void setUp() throws Exception {
        user = new RegularUser();
        outContent = new ByteArrayOutputStream();
        // Redirecting System.out and System.err to capture printed output
        System.setOut(new PrintStream(outContent));
        originalOut = System.out;
    }
    @After
    public void tearDown() throws Exception {
        // Restoring System.out
        System.setOut(originalOut);
    }
    @Test
    public void testEventExists_Positive() {
        // Prepare test data
        //Enter a valid input with valid event name
        String input = "Test Event\nno";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        // Perform the test
        user.SearchAndGetTicket();
        // Check the output
        assertTrue(outContent.toString().contains("Event(s) found with the name"));
        // Restore System.out
    }

    @Test
    public void testEventExists_Negative() {
        // Prepare test data
        //Enter input with no such event exist in database
        String input = "irregular\nno";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        // Perform the test
        user.SearchAndGetTicket();
        // Check the output
        assertTrue(outContent.toString().contains("No event found with the name"));
        // Restore System.out
    }

}
