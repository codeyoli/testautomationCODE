package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/***
 * Wrapper class that abstracts the low level selenium code for achieving great
 * level of abstracting to the testers. Please see provided documentary from Zulfi
 * tech on Box platform for tutorials and detailed documentation.
 *
 *
 * @author  Nijat Muhtar
 * Last updated:  Feb 11, 2023
 *
 */
public class Automation {

    static private final Duration timeLimit = Duration.ofSeconds(5);
    @Getter static private WebDriver driver;
    static private WebDriverWait waits;
    static private String windowHandle;


    static public By $(String query) {
        if(query.contains("//") || query.contains("@")) {
            return By.xpath(query);
        }
        else {
            return By.cssSelector(query);
        }
    }

    // --- browser Related --- //
    static public class browser {

        static public void openWithDocker() {
            WebDriverManager config = WebDriverManager
                    .chromedriver()
                    .browserInDocker()
                    .dockerScreenResolution("1920x1080x24")
                    .enableRecording()
                    .dockerRecordingOutput(util.root() + "/video/");
            //config.enableVnc();
            config.config().setDockerRecordingPrefix(TestDetection.currentTestCaseName +"__");
            driver = config.create();
            waits = new WebDriverWait(driver, timeLimit);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().window().setSize(new Dimension(1920, 1080));
            //System.out.println("See test run at: " + config.getDockerNoVncUrl());
        }



        static public void open() {
            String browserChoice = TestConfig.extract("$.browser.choice");
            boolean isHeadless = TestConfig.extract("$.browser.headless");
            driver = util.driverType(browserChoice, isHeadless);
            waits = new WebDriverWait(driver, timeLimit);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

        static public void maxmize() {
            driver.manage().window().maximize();
        }

        static public void openChrome() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            waits = new WebDriverWait(driver, timeLimit);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

        static public void close() {
            if (driver == null) return;
            driver.quit();
        }

        static public void refresh() {
            driver.navigate().refresh();
        }

        static public void nextPage() {
            driver.navigate().forward();
        }

        static public void previousPage() {
            driver.navigate().back();
        }

        static public void acceptPopUp() {
            Alert alert = waits.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        }
    }//browser



    static public class user {

        static public void visits(String url) {
            driver.manage().timeouts().pageLoadTimeout(timeLimit);
            driver.get(url);
        }

        static public WebElement findsElement(By locator) {
            WebElement elem;
            try{
                elem = waits.until(ExpectedConditions.visibilityOfElementLocated(locator));
            }catch (StaleElementReferenceException error) {
                waits.withMessage("Could not find the element -> " + locator.toString());
                elem = waits.until(ExpectedConditions.visibilityOfElementLocated(locator));
            }
            return elem;
        }

        static public List<WebElement> findsElements(By locator) {
            List<WebElement> elems = waits.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
            return elems;
        }

        static public void clicks(By locator) {
            WebElement found = findsElement(locator);
            found = waits.until(ExpectedConditions.elementToBeClickable(found));
            highlight(found);
            found.click();
        }

        static public void clicks(WebElement element) {
            WebElement found = waits.until(ExpectedConditions.elementToBeClickable(element));
            found.click();
        }


        static public void types(By locator, String text) {
            WebElement found = findsElement(locator);
            highlight(found);
            found.sendKeys(text);
        }


        static public void types(WebElement element, String text) {
            WebElement found = waits.until(ExpectedConditions.visibilityOf(element));
            found.sendKeys(text);
        }

        static public void highlight(By locator) {
            WebElement found = findsElement(locator)
;            JavascriptExecutor jse = (JavascriptExecutor) driver;
            String script = "arguments[0].setAttribute('style', 'border: 2px solid purple');";
            jse.executeScript(script, found);
        }

        static public void highlight(WebElement element) {
            WebElement found = waits.until(ExpectedConditions.visibilityOf(element));
            JavascriptExecutor jse = (JavascriptExecutor)driver;
            String script = "arguments[0].setAttribute('style', 'border: 2px solid purple');";
            jse.executeScript(script, found);
        }

        static public void opensTabWith(String title) {
            Set<String> ids = driver.getWindowHandles();
            for (String each : ids) {
                driver.switchTo().window(each);
                String ret = driver.getTitle();
                if (ret.contains(title)) {
                    break;
                }
            }//end::for
        }

        static public void changeToFrame() {
            time.pauseSec(2);
            By loc_iframe = $("//iframe");
            windowHandle = driver.getWindowHandle();
            WebElement frame = findsElement(loc_iframe);
            if(frame.isDisplayed()){
                driver.switchTo().defaultContent();
                driver.switchTo().frame(frame);
            }
        }

        static public void changeToMainPage() {
            driver = driver.switchTo().defaultContent();
        }

        static public void opensTabWithUrl(String urlPart) {
            Set<String> ids = driver.getWindowHandles();
            for (String each : ids) {
                driver.switchTo().window(each);
                String wholeUrl = driver.getCurrentUrl();
                boolean isPart = wholeUrl.contains(urlPart);
                if (isPart) break;
            }//end::for
        }

        static public boolean canSee(By locator) {
            WebElement element = findsElement(locator);
            return element.isDisplayed();
        }

        static public String asksTextOf(By locator) {
            WebElement found = findsElement(locator);
            return found.getText();
        }

        static public List<String> asksAllTextOf(By locator) {
            List<WebElement> found = findsElements(locator);
            List<String> texts = new ArrayList<>();
            for(WebElement e : found) {
                texts.add(e.getText());
            }
            return texts;
        }
    }//user


    static public class time {

        static public void pauseSec(int second) {
            try {
                Thread.sleep(second * 1000L);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }

        static public void pauseMin(int minute) {
            try {
                long duration = (long) minute * 60 * 1000;
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }
    }//time

    static public class util {

        static public String root() {
            return System.getProperty("user.dir");
        }

        static public String excelPath(String file) {
            String path = root() + "/excels/";
            return path + file;
        }

        static public WebDriver driverType(String choice, boolean isHeadless) {
            boolean isChrome = choice.equalsIgnoreCase("chrome");
            boolean isChromium = choice.equalsIgnoreCase("chromium");
            boolean isFirefox = choice.equalsIgnoreCase("firefox");
            boolean isEdge = choice.equalsIgnoreCase("edge");
            boolean isSafari = choice.equalsIgnoreCase("safari");

            if (isChrome || isChromium) {
                var options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("no-sandbox");
                //options.addArguments("--headless");
                options.addArguments("disable-gpu");
                options.addArguments("window-size=1900,1080");
                options.addArguments("window-size=1900,1080");
                WebDriverManager.chromedriver().setup();
                if (isHeadless) return new ChromeDriver(options);
                else return new ChromeDriver(options);
            } else if (isFirefox) {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setHeadless(true);
                if (isHeadless) return new FirefoxDriver(firefoxOptions);
                else return new FirefoxDriver();
            } else if (isEdge) {
                WebDriverManager.edgedriver().setup();
                return new EdgeDriver();
            } else if (isSafari) {
                String osType = System.getProperty("os.name");
                boolean isMac = osType.toLowerCase().contains("OS X")
                        || osType.toLowerCase().contains("Mac");
                if (isMac) {
                    WebDriverManager.safaridriver().setup();
                }
                return null;
            } else {
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();
            }
        }

        /**
         * Use this to delete the recorded video of passed test cases. This method is effective when
         * the video recording is enabled for test execution and TestDetection is registered as test listener.
         *
         * @param prefix video prefix text
         */
        static public void deletePassedRecordings(String prefix) {
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
    }//uil
}//end::class
// https://github.com/dhatim/fastexcel