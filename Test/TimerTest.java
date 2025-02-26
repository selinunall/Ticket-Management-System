import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    private Timer testLogoutTimer;
    private static final long TIMEOUT = 20 * 1000; // 20 seconds in milliseconds
    private boolean isLoggedOut;

    @Before
    public void setUp() {
        testLogoutTimer = new Timer();
        isLoggedOut = false; // Kullanıcı oturum açtığında bayrağı false yaparız
        // Simulate user login and start timer
        startLogoutTimer();
    }

    @After
    public void tearDown() {
        // Stop the timer and simulate user logout
        testLogoutTimer.cancel();
    }

    @Test
    public void testResetLogoutTimer_Positive() {
        resetLogoutTimer();

        try {
            // Wait a short time (e.g., 5 seconds) and then reset the timer again
            Thread.sleep(5000);
            resetLogoutTimer();

            // Wait for slightly less than the timeout period to ensure no logout occurs
            Thread.sleep(TIMEOUT - 10000);
            // If we reach here without logging out, the test passes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertFalse("User should still be logged in", isUserLoggedOut());
    }

    @Test
    public void testLogoutAfterTimeout_Negative() {
        try {
            // Wait for the full timeout period to ensure logout occurs
            Thread.sleep(TIMEOUT + 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue("User should be logged out due to inactivity", isUserLoggedOut());
    }

    private void startLogoutTimer() {
        testLogoutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("User has been logged out due to inactivity.");
                simulateUserLogout();
            }
        }, TIMEOUT);
    }

    private void resetLogoutTimer() {
        if (testLogoutTimer != null) {
            testLogoutTimer.cancel();
        }
        testLogoutTimer = new Timer();
        startLogoutTimer();
    }

    private boolean isUserLoggedOut() {
        // Kullanıcının çıkış yapıp yapmadığını bayrağa göre kontrol ederiz
        return isLoggedOut;
    }

    private void simulateUserLogout() {
        // Kullanıcının çıkış yaptığını simüle ederken bayrağı true yaparız
        isLoggedOut = true;
        System.out.println("Simulating user logout...");
    }
}

/*Pozitif Test: Kullanıcı etkileşimi sırasında zamanlayıcının sıfırlandığını ve kullanıcının oturumunun açık kaldığını doğrular.
Negatif Test: Kullanıcı hiçbir işlem yapmadığında belirli bir süre sonra (20 saniye) kullanıcının oturumunun otomatik olarak kapatıldığını
doğrular.*/