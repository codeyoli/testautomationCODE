package core;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.*;

import java.io.File;


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
        deletePassedRecordings(name);
    }

    public void onTestFailure(ITestResult result) {

    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedWithTimeout(ITestResult result) {

    }


    /*
     * Use this to delete the recorded video of passed test cases. This method is effective when
     * the video recording is enabled for test execution and TestDetection is registered as test listener.
     *
     */
    private void deletePassedRecordings(String prefix) {
        File directory = new File(Automation.util.root()+"/video/");
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().startsWith(prefix)) {
                if (file.delete()) {
                    String message = "The file " + file.getName() + " was successfully deleted";
                    System.out.println(message);
                } else {
                    String message = "The file " + file.getName() + " could not be deleted";
                    System.out.println(message);
                }
            }
        }//end::for
    }
}//end::class