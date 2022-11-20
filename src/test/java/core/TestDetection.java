package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.*;


public class TestDetection
        implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private ExtentReports reports;
    private ExtentSparkReporter spark;
    private ExtentTest testCase;


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
        this.testCase = reports.createTest(name);
        Steps.init(this.testCase);
    }

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {

    }

    // --- Test Case Result --- //
    public void onTestSuccess(ITestResult result) {

    }

    public void onTestFailure(ITestResult result) {

    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }


}//end::class
