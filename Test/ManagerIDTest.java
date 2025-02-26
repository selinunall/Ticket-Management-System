import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagerIDTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private InputStream standardInputStream;
    private EventManagementSystem system;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        standardInputStream = System.in;
        system = new EventManagementSystem();
    }

    @After
    public void tearDown() {
        System.setIn(standardInputStream);
    }

    @Test
    public void testRegisterAsEventManager_Positive() {
        // Generate a unique manager ID
        int managerId = generateUniqueId();

        // Prepare input for the test
        String simulatedInput = managerId + "\npassword\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Run the method to be tested
        system.registerAsEventManager();
        assertTrue(outContent.toString().contains("Manager registered successfully."));

        // Write assertions to verify the result
        // You can add assertions based on the expected behavior of the system
    }
    @Test
    public void testRegisterAsEventManager_Negative() {
        // Generate a unique manager ID
        int managerId = 1;

        // Prepare input for the test
        String simulatedInput = managerId + "\npassword\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Run the method to be tested
        system.registerAsEventManager();
        assertTrue(outContent.toString().contains("This ID is already registered."));

        // Write assertions to verify the result
        // You can add assertions based on the expected behavior of the system
    }



    // Helper method to generate a unique manager ID
    private int generateUniqueId() {
        return (int) (Math.random() * 100000) + 10000; // Generate a random ID between 10000 and 99999
    }
}
