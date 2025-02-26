
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

public class TicketChangeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    public void testChangeTicket_PositiveScenario() {
        // Prepare test data
        int ticketId1 = 70; // Assuming ticket ID 1 exists and is purchased by a user
        int newTicketId = 71; // Assuming ticket ID 10 exists and is available

        // Perform the test
        RegularUser regularUser = new RegularUser();
        regularUser.changeTicket(ticketId1, newTicketId);
        assertTrue(outContent.toString().contains("User email successfully transferred to the new ticket."));

        // Add your assertions here based on the expected behavior of changeTicket method
    }

    @Test
    public void testChangeTicket_NegativeScenario() {
        // Prepare test data
        int ticketId1 = 75; // Assuming ticket ID 999 does not exist or is not purchased by any user
        int newTicketId = 10; // Assuming ticket ID 10 exists and is available

        // Perform the test
        RegularUser regularUser = new RegularUser();
        regularUser.changeTicket(ticketId1, newTicketId);
        assertTrue(outContent.toString().contains("Failed to transfer user email to the new ticket."));


        // Add your assertions here based on the expected behavior of changeTicket method
    }
}
