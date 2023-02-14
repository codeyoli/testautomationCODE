package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import core.utils.FileManager;
import core.utils.Logs;
import org.testng.*;


public class TestDetection
        implements ISuiteListener, ITestListener {

    private ExtentReports reports;
    private ExtentSparkReporter spark;
    public static ExtentTest testCase;
    public static String currentTestCaseName;


    // --- Test Suite Related --- //
    public void onStart(ISuite suite) {
        String path = System.getProperty("user.dir") + "/reports/";
        this.reports = new ExtentReports();
        this.spark = new ExtentSparkReporter(path);
        this.spark.config().setTimelineEnabled(true);
        this.spark.config().thumbnailForBase64(true);
        this.spark.config().setReportName(suite.getName());
        this.reports.attachReporter(spark);
        this.reports.setReportUsesManualConfiguration(true);
    }

    public void onFinish(ISuite suite) {
        this.reports.flush();
    }


    // --- Test Cycle Related --- //
    public void onStart(ITestContext context) {
        reports.setSystemInfo("OS", System.getProperty("os.name"));
        reports.setSystemInfo("User", System.getProperty("user.name"));
        reports.setSystemInfo("Java", System.getProperty("java.version"));
        reports.setSystemInfo("Project Root", System.getProperty("user.dir"));

    }

    public void onFinish(ITestContext context) {
        for (String s : Reporter.getOutput()) {
            reports.addTestRunnerOutput(s);
        }
    }


    // ---- Before Individual Test Case Execution --- //
    public void onTestStart(ITestResult testCase) {
        String name = testCase.getName();
        currentTestCaseName = name;
        this.testCase = reports.createTest(name);
        Logs.init(this.testCase);
    }

    // --- Test Case Result --- //
    public void onTestSuccess(ITestResult result) {
        String name = result.getName();

    }

    public void onTestFailure(ITestResult result) {
        String name = result.getName();;
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }

}//end::class