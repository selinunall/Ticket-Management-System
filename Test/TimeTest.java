import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class TimeTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // Custom method to validate the time string
    public static boolean isValidTime(String time) {
        // Check if the format matches HH:mm:ss
        if (!time.matches("\\d{2}:\\d{2}:\\d{2}")) {
            return false;
        }

        // Split the time into parts
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        int seconds = Integer.parseInt(parts[2]);

        // Validate hours, minutes, and seconds range
        return (hours >= 0 && hours < 24) && (minutes >= 0 && minutes < 60) && (seconds >= 0 && seconds < 60);
    }

    // Positive Test
    @Test
    public void testTimeFormatPositive() {
        assertTrue(isValidTime("12:34:56"));
    }

    // Negative Tests
    @Test
    public void testIncorrectHourFormat() {
        assertFalse(isValidTime("25:00:00"));
    }

    @Test
    public void testIncorrectMinuteFormat() {
        assertFalse(isValidTime("12:60:00"));
    }

    @Test
    public void testIncorrectSecondFormat() {
        assertFalse(isValidTime("12:34:60"));
    }

    @Test
    public void testMissingPartsOfTime() {
        assertFalse(isValidTime("12:34"));
        assertFalse(isValidTime("12:34:"));
    }

    @Test
    public void testIncorrectSeparator() {
        assertFalse(isValidTime("12-34-56"));
    }

    @Test
    public void testNonNumericCharacters() {
        assertFalse(isValidTime("12:34:5a"));
    }

    @Test
    public void testSpacesInTimeString() {
        assertFalse(isValidTime("12: 34:56"));
    }
}