import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DateTest {
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

    // Custom method to validate the date string
    public static boolean isValidDate(String date) {
        // Check if the format matches yyyy-MM-dd
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }

        // Split the date into parts
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        // Validate the month range
        if (month < 1 || month > 12) {
            return false;
        }

        // Validate the day range
        if (day < 1) {
            return false;
        }

        // Days in each month
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        // Leap year check for February
        if (month == 2 && (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0))) {
            daysInMonth[1] = 29;
        }

        // Validate the day against the maximum days in the month
        return day <= daysInMonth[month - 1];
    }

    // Positive Tests
    @Test
    public void testEventDateFormatPositive() {
        assertTrue(isValidDate("2024-05-24"));
    }

    @Test
    public void testLeapYearDatePositive() {
        assertTrue(isValidDate("2020-02-29"));
    }

    // Negative Tests
    @Test
    public void testIncorrectYearFormatNegative() {
        assertFalse(isValidDate("24-05-2024"));
    }

    @Test
    public void testIncorrectMonthFormatNegative() {
        assertFalse(isValidDate("2024-13-24"));
    }

    @Test
    public void testIncorrectDayFormatNegative() {
        assertFalse(isValidDate("2024-05-32"));
    }

    @Test
    public void testLeapYearDateNegative() {
        assertFalse(isValidDate("2021-02-29"));
    }

    @Test
    public void testNonNumericCharactersNegative() {
        assertFalse(isValidDate("2024-05-2a"));
    }

    @Test
    public void testIncorrectSeparatorNegative() {
        assertFalse(isValidDate("2024/05/24"));
    }

    @Test
    public void testMissingPartsOfDateNegative() {
        assertFalse(isValidDate("2024-05"));
        assertFalse(isValidDate("2024-05-"));
    }

    @Test
    public void testSpacesInDateStringNegative() {
        assertFalse(isValidDate("2024- 5-24"));
    }
}