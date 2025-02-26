import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class SearchTestCategory {

    private final InputStream originalIn = System.in;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        // Redirecting System.out and System.in to capture printed output and provide input
        System.setOut(new PrintStream(outContent));

        System.setIn(new ByteArrayInputStream("konser\nyes\n7\n".getBytes()));
    }

    @After
    public void restoreStreams() {
        // Restoring original System.out and System.in
        System.setOut(System.out);
        System.setIn(originalIn);
    }

    @Test
    public void testSearchAndFilterCategory_Successful() {
        // Simulating a successful search for events with the category 'konser'
        RegularUser user = new RegularUser();
        user.SearchAndFilterCategory();
        assertTrue(outContent.toString().contains("Event(s) found with the category '" + "konser" + "':"));


        // Checking if the console output contains the expected message for successful search        assertTrue(outContent.toString().contains("Event(s) found with the category 'konser'"));
    }

    @Test
    public void testSearchAndFilterCategory_Unsuccessful() {
        System.setIn(new ByteArrayInputStream("NonexistentCategory\n".getBytes()));
        // Simulating an unsuccessful search for events with a nonexistent category
        RegularUser user = new RegularUser();
        user.SearchAndFilterCategory();

        // Checking if the console output contains the expected message for unsuccessful search
        assertTrue(outContent.toString().contains("No event found with the category 'NonexistentCategory'"));
    }
    // This test class validates the search and filter functionality by category in the RegularUser class.
    // It includes both successful and unsuccessful search scenarios to ensure correct behavior.
}
