import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AddEventTest.class, DeleteEventTest.class // Assuming you name your test class RegularUserTest
})

public class EventProcessSuite {
    // This test suite combines multiple test classes related to event processing.
    // It includes tests for adding events, deleting events, and possibly other related functionalities.
    // By running this suite, you can verify the correctness and integrity of event processing features.
}

