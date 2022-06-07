package base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import data.TrelloFaker;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverUtil;
import utils.TestDetector;

import static utils.Automations.init;
import static utils.DriverUtil.*;

/**
 * This is a parent class for all the test class that contains test cases.
 * Methods contained in this class should be used in each individual test cases.
 * As such, this class is declared as [abstract] to disable an object creation.
 */
public abstract class TestBase {

    private PageManager pages;
    private TrelloFaker faker;

    /**
     * Use this method in your test script
     * to take a screenshot steps in the report.
     *
     * @param title
     */
    protected static void snap(String title) {
        TakesScreenshot scDriver = (TakesScreenshot) DriverUtil.getDriver();
        String pic = scDriver.getScreenshotAs(OutputType.BASE64);
        Media screenshotPic = MediaEntityBuilder
                .createScreenCaptureFromBase64String(pic, title)
                .build();
        TestDetector.testCaseExecution().info(screenshotPic);
    }

    /**
     * Use this method in your test script to take a
     * step description in your execution report
     *
     * @param message
     */
    protected static void log(String message) {
        TestDetector.testCaseExecution().info(message);
    }

    /**
     * Use this class to get access to every page object
     * at your disposal in your test case.
     *
     * @return
     */
    public PageManager at() {
        boolean pagesNeverUsed = (pages == null);
        if (pagesNeverUsed) {
            pages = new PageManager();
            return pages;
        }

        // pages object was created before
        // return that previous object instead
        return pages;
    }

    /**
     * Use this class to get access to the custom faker
     * object that has random data catered to the Trello page.
     *
     * @return
     */
    public TrelloFaker faker() {
        boolean fakerIsNeverUsed = (faker == null);
        if (fakerIsNeverUsed) {
            faker = new TrelloFaker();
            return faker;
        }

        // faker object was created before
        // return that previous object instead
        return faker;
    }

    @BeforeMethod
    protected void setUp() {
        openBrowser("chrome");
        init(getDriver());
    }

    @AfterMethod
    protected void cleanUp() {
        closeBrowser();
    }
}//end::class