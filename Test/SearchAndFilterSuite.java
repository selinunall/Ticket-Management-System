import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
       SearchTestEvent.class, SearchTestCategory.class
})
public class SearchAndFilterSuite {
}
// This test suite combines multiple test classes related to searching and filtering events.
// It includes tests for searching events and filtering events by category.
// By running this suite, you can verify the correctness and integrity of search and filter functionalities.