import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class TicketPriceTest {
    private InputStream inputStream;
    private ByteArrayOutputStream outContent;
    private RegularUser user;
    private PrintStream originalOut;

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
    public void numberOfTicket_Positive() {
        // Prepare test data
        //Name event which has ticket is bigger or equal then 0
        String input = "burak\nyes\n52";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        // Perform the test
        user.SearchAndGetTicket();
        // Check the output
        assertTrue(outContent.toString().contains("Ticket added to cart successfully."));
        // Restore System.out
    }

    @Test
    public void numberOfTicket_Negative() {
        // Prepare test data
        //Name event which has number of ticket is less then  0
        String input = "selin\nyes\n53";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        // Perform the test
        user.SearchAndGetTicket();
        // Check the output
        assertTrue(outContent.toString().contains("A systemic error has occurred,"));
        // Restore System.out
    }

}