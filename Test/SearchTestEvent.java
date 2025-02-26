import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertTrue;

public class SearchTestEvent {

    private final InputStream originalIn = System.in;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }
    // Redirecting System.out to capture printed output
    @After
    public void restoreStreams() {        // Restoring original System.out and System.in
        System.setOut(System.out);
        System.setIn(originalIn);
    }

    @Test
    public void testSearchAndFilterEvent_Successful() {
        // Prepare input stream with simulated input
        String input = "duman\nyes\n7\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        //  Create RegularUser object and call the method
        RegularUser user = new RegularUser();
        user.SearchAndGetTicket();

        // Check the output
        assertTrue(outContent.toString().contains("Event(s) found with the name 'duman'"));
    }

    @Test
    public void testSearchAndFilterEvent_Unsuccessful() {
        // Prepare input stream with simulated input
        String input = "NonexistentEventName\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Create RegularUser object and call the method
        RegularUser user = new RegularUser();
        user.SearchAndGetTicket() ;

        // Check the output
        assertTrue(outContent.toString().contains("No event found with the name 'NonexistentEventName'"));
    }
}
