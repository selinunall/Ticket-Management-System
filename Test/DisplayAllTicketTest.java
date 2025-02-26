import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;

public class DisplayAllTicketTest {
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;
    private RegularUser displayAllTicket ;


    @Before
    public void setUp() throws Exception {
        displayAllTicket = new RegularUser();
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        // Redirecting System.out and System.err to capture printed output
        System.setOut(new PrintStream(outContent));
        System.setErr((new PrintStream(errContent)));
    }

    @After
    public void tearDown() throws Exception {
        // Restoring System.out and System.err
        System.setOut(originalOut);
        System.setErr(originalErr);

        // Restoring System.in
        System.setIn(originalIn);
    }

    @Test
    public void testDisplayAllTicket_Positive() {
        // Redirecting System.in to provide input
        String input = "burak@hotmail.com";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Assuming there are tickets associated with the user's email in the database
        displayAllTicket.DisplayAllTicket();

        // Add assertions here to validate the output printed by DisplayAllTicket method
        assertFalse(outContent.toString().contains("eventName"));
        assertFalse(outContent.toString().contains("eventTime"));
        assertFalse(outContent.toString().contains("eventLocation"));
        assertFalse(outContent.toString().contains("eventCategory"));
        assertFalse(outContent.toString().contains("eventDate"));
    }

    @Test
    public void testDisplayAllTicket_Negative() {
        // Redirecting System.in to provide input
        String input = "nonexistent_user@gmail.com";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // Assuming there are no tickets associated with the user's email in the database
        displayAllTicket.DisplayAllTicket();

        // Add assertions here to validate the output printed by DisplayAllTicket method
        assertFalse(outContent.toString().contains("eventName"));
        assertFalse(outContent.toString().contains("eventTime"));
        assertFalse(outContent.toString().contains("eventLocation"));
        assertFalse(outContent.toString().contains("eventCategory"));
        assertFalse(outContent.toString().contains("eventDate"));
    }
    // This class tests the DisplayAllTicket method in the RegularUser class.
    // It includes positive and negative scenarios to ensure correct behavior
    // when displaying tickets for a given user.
}