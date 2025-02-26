import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserDeletionTest.class ,UserRegistrationTest.class , UserTestLogin.class , // Assuming you name your test class RegularUserTest
})
public class UserProcessTestSuite {
}
/**
 * Test suite for testing the user processes including registration, login, and deletion.
 * Includes tests from UserRegistrationTest, UserLoginTest, and UserDeletionTest classes.
 */