package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class contains driver related common methods
 * that will be used in test scripts.
 *
 * @Author Nijat Muhtar
 * @Date 05/22/2022
 */
public class DriverUtil {

    private static WebDriver driver;

    /**
     * Use this method to create driver-->browser connection. After
     * invoking this method, the driver refers to the driver object
     *
     * @param browserType [chrome, edge, firefox, headless]
     */
    public static void openBrowser(String browserType) {
        // determine the chosen browser
        boolean isChrome = browserType.equalsIgnoreCase("chrome");
        boolean isEdge = browserType.equalsIgnoreCase("edge");
        boolean isFirefox = browserType.equalsIgnoreCase("firefox");
        boolean isHeadlessChrome = browserType.equalsIgnoreCase("headless");

        if (isChrome) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (isEdge) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else if (isFirefox) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (isHeadlessChrome) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--window-size=1920,1200");
            options.addArguments("--disable-extensions");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }
    }

    /**
     * Use this method to access the driver instance
     *
     * @return
     */
    public static WebDriver getDriver() {
        return driver;
    }


    /**
     * Use this method to terminate the driver --> browser
     * connection and quit the browser.
     */
    public static void closeBrowser() {
        driver.quit();
    }

    /**
     * Use this method to close the current window
     * that the driver is focused on.
     */
    public static void closeWindow() {
        driver.close();
    }

    /**
     * Use this method to refresh the current window
     */
    public static void refresh() {
        driver.navigate().refresh();
    }


}//end::class
