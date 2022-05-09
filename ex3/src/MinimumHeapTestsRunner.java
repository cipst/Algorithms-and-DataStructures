import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MinimumHeapTestsRunner {
    public static void main(String[] args) {
        testMinimumHeapInteger();
        testMinimumHeapString();
    }

    private static void testMinimumHeapInteger() {
        Result result = JUnitCore.runClasses(MinimumHeapTestsInteger.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }

    private static void testMinimumHeapString() {
        Result result = JUnitCore.runClasses(MinimumHeapTestsString.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
