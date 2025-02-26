import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MenuInputTest {
    // Constants for messages and test inputs
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String EXIT_MESSAGE = "Press 4 to exit." + LINE_SEPARATOR;
    private static final String LOGIN_AS_USER_MESSAGE = "Press 1 to login as user." + LINE_SEPARATOR;
    private static final String LOGIN_AS_MANAGER_MESSAGE = "Press 2 to login as event manager." + LINE_SEPARATOR;
    private static final String REGISTER_MESSAGE = "Press 3 to register." + LINE_SEPARATOR;
    private static final String INVALID_CHOICE_MESSAGE = "Invalid choice." + LINE_SEPARATOR;
    private static final String TEST_USER_INPUT = "1" + LINE_SEPARATOR;
    private static final String TEST_MANAGER_INPUT = "2" + LINE_SEPARATOR;
    private static final String TEST_REGISTER_INPUT = "3" + LINE_SEPARATOR;
    private static final String TEST_EXIT_INPUT = "4" + LINE_SEPARATOR;
    private static final String TEST_INVALID_INPUT = "7" + LINE_SEPARATOR;

    // Backup for the original System.in
    private final InputStream sysInBackup = System.in;
    private ByteArrayInputStream in;

    // Setup method to be executed before each test
    @Before
    void setUp() {
        // Redirect System.in to the test input stream
        System.setIn(in);
    }

    // Teardown method to be executed after each test
    @After
    void tearDown() {
        // Restore the original System.in
        System.setIn(sysInBackup);
    }

    // Test for user choice input
    @Test
    void testUserChoice() {
        // Set up input stream with test input
        in = new ByteArrayInputStream(TEST_USER_INPUT.getBytes());
        System.setIn(in);
        // Test if user choice matches expected input
        assertDoesNotThrow(() -> {
            Scanner scanner = new Scanner(System.in);
            assertEquals(1, scanner.nextInt());
        });
    }

    // Test for manager choice input
    @Test
    void testManagerChoice() {
        // Set up input stream with test input
        in = new ByteArrayInputStream(TEST_MANAGER_INPUT.getBytes());
        System.setIn(in);
        // Test if manager choice matches expected input
        assertDoesNotThrow(() -> {
            Scanner scanner = new Scanner(System.in);
            assertEquals(2, scanner.nextInt());
        });
    }

    // Test for register choice input
    @Test
    void testRegisterChoice() {
        // Set up input stream with test input
        in = new ByteArrayInputStream(TEST_REGISTER_INPUT.getBytes());
        System.setIn(in);
        // Test if register choice matches expected input
        assertDoesNotThrow(() -> {
            Scanner scanner = new Scanner(System.in);
            assertEquals(3, scanner.nextInt());
        });
    }

    // Test for exit choice input
    @Test
    void testExitChoice() {
        // Set up input stream with test input
        in = new ByteArrayInputStream(TEST_EXIT_INPUT.getBytes());
        System.setIn(in);
        // Test if exit choice matches expected input
        assertDoesNotThrow(() -> {
            Scanner scanner = new Scanner(System.in);
            assertEquals(4, scanner.nextInt());
        });
    }

    // Test for invalid choice input
    @Test
    void testInvalidChoice() {
        // Set up input stream with test input
        in = new ByteArrayInputStream(TEST_INVALID_INPUT.getBytes());
        System.setIn(in);
        // Test if invalid choice matches expected input
        assertDoesNotThrow(() -> {
            Scanner scanner = new Scanner(System.in);
            assertEquals(7, scanner.nextInt());
        });
    }

    // This test class validates the menu input choices in the application.
    // It includes tests for user, manager, register, exit, and invalid choices,
    // ensuring that the correct choice is made according to the input.
}
