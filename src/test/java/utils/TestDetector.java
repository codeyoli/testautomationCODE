package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class TestDetector implements ITestListener {

    private String file = System.getProperty("user.dir") + "/reports/";

    private static ExtentTest testExecution;
    private ExtentReports reportManager = new ExtentReports();
    private ExtentSparkReporter spark = new ExtentSparkReporter(file);


    // ---- Test Context related detections ---//
    // Contains all the information for a given test run.
    // An instance of this context is passed to the test listener's implementation
    // so they can query information about their environment.
    @Override
    public void onStart(ITestContext context) {
        spark.config().setDocumentTitle("Alphaleaf Automated Tests");
        spark.config().setTimelineEnabled(true);
        spark.config().setReportName(context.getName());
        reportManager.attachReporter(spark);
    }

    @Override
    public void onFinish(ITestContext context) {
        reportManager.flush();
    }

    // ---- Test Case Before Execution  ---//
    @Override
    public void onTestStart(ITestResult testCase) {
        String name = testCase.getName();
        testExecution = reportManager.createTest(name);
    }

    public static ExtentTest testCaseExecution() {
        return testExecution;
    }


    // ---- Test Case After Execution --- //
    public void onTestSuccess(ITestResult result) {
        testExecution.pass("It has passed");
    }

    public void onTestFailure(ITestResult result) {
        testExecution.fail("Test case resulted in failure.");
        testExecution.fail( result.getThrowable() );
    }

    public void onTestSkipped(ITestResult result) {

    }
}//end:class
