import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class TurkishLanguageTest {
    private EventManagementSystem system;
    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;

    @Before
    public void setUp() {
        system = new EventManagementSystem();
    }

    @After
    public void tearDown() {
        system = null;
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    @Test
    public void testLoginAsUser_PositiveScenario() {
        // Simulate user input
        ByteArrayInputStream in = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the main method
        system.main(null);

        // Check if Turkish output is printed
        assertTrue(outContent.toString().contains("user table created successfully."));
    }

    @Test
    public void testLoginAsUser_NegativeScenario() {
        // Simulate user input
        ByteArrayInputStream in = new ByteArrayInputStream("5\n".getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the main method
        system.main(null);

        // Check if Turkish output is not printed
        assertFalse(outContent.toString().contains("user table created successfully."));
    }
}
