import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ManagerIDTest.class, ManagerTestLogin.class // Assuming you name your test class RegularUserTest
})
public class ManagerProcessTestSuite {
    // This test suite combines multiple test classes related to manager processes.
    // It includes tests for manager registration and login functionalities.
    // By running this suite, you can verify the correctness and integrity of manager-related features.
}
