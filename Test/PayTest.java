import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PayTest {
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private final InputStream originalIn = System.in;
    private RegularUser regularUser;

    @Before
    public void setUp() throws Exception {
        regularUser = new RegularUser();
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        // Redirecting System.out and System.err to capture printed output
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
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
    public void testPay_Positive() {
        List<Ticket> tickets = new ArrayList<>();
        // Adding some sample tickets
        tickets.add(new Ticket(1, 100, 1, "burak@hotmail.com"));
        tickets.add(new Ticket(2, 20, 2, "burak2@hotmail.com"));
        regularUser.pay(tickets);

        // Verify the output printed by pay method
        assertEquals("Total price is120\r\nPayment is done :)\r\n", outContent.toString());
    }

    @Test
    public void testPay_Negative() {
        List<Ticket> emptyTicketList = new ArrayList<>();

        regularUser.pay(emptyTicketList);

        // Verify the output printed by pay method when there are no tickets
        assertEquals("Total price is0\r\nPayment is done :)\r\n", outContent.toString());
    }
    // This test class validates the pay method in the RegularUser class.
    // It includes positive and negative scenarios to ensure correct behavior
    // when paying for tickets.
}
