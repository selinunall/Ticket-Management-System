import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class RefundTicketTest {
    private RegularUser ticket;
    private ByteArrayOutputStream outContent;

    @Before
    public void setUp() {
        ticket = new RegularUser();
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(System.out);
    }

    @Test
    public void testSuccessfulTicketRefund() {
        // Define an example ticketId
        int ticketId = 117;
        ticket.refundTicket(ticketId);
        // Check if the ticket is successfully refunded
        assertTrue(outContent.toString().contains("Ticket successfully refunded."));
    }

    // Testing the failed refund of a ticket
    @Test
    public void testFailedTicketRefund() {
        // Define an invalid ticketId (e.g., a non-existing ticketId)
        int invalidTicketId = -1;
        ticket.refundTicket(invalidTicketId);
        // Check if the ticket is not refunded
        assertTrue(outContent.toString().contains("Failed to refund the ticket."));
    }
}