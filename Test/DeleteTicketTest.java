
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import static org.junit.Assert.assertEquals;

public class DeleteTicketTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final ByteArrayInputStream inContent = new ByteArrayInputStream("1\n".getBytes());



    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        System.setIn(inContent);
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
        System.setIn(System.in);
    }

    @Test
    public void testDeleteTicket() {
        int number = 17;
        EventManager eventManager = new EventManager();
        eventManager.deleteTicket(number); // Gerçek bir eventId girilmeli
        assertEquals("All tickets for " + number + " deleted successfully.\r\n", outContent.toString());
    }

    @Test
    public void testDeleteTicketInvalidEventId() {
        EventManager eventManager = new EventManager();
        eventManager.deleteTicket(1000); // Geçersiz bir eventId girildi
        assertEquals("No event found with the given ID.\r\n", outContent.toString());

    }



}