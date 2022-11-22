package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class that abstracts the general UI automation activities
 * Please reach the provided documentation before using this class
 *
 * @Author Nijat Muthar, ZULFI TECH, LLC
 * @Date 11/22/2022
 */
public class Automation {

    static private final Duration timeLimit = Duration.ofSeconds(20);
    static private final Duration elemTimeLimit = Duration.ofSeconds(5);
    @Getter
    static private WebDriver driver;
    static private FluentWait fluentWait;

    // --- browser Related --- //
    static public class browser {

        static public void open() {
            ConfigManager config = new ConfigManager();
            String browserChoice = config.extract("$.browser.choice");
            boolean isHeadless = config.extract("$.browser.headless");
            driver = util.driverType(browserChoice, isHeadless);
            fluentWait = new FluentWait(driver);
            fluentWait.withTimeout(elemTimeLimit);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        }

        static public void openChrome() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            fluentWait = new FluentWait(driver);
            fluentWait.withTimeout(elemTimeLimit);
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
    }//browser


    static public class elem {

        static public By xp(String xpath) {
            return By.xpath(xpath);
        }

        static public By css(String query) {
            return By.cssSelector(query);
        }

        static public By id(String value) {
            return By.id(value);
        }

        static public Elem at(String address) {
            var excel = new Excel(util.excelPath("locations.xlsx"));
            var e = new Elem();
            String[] parts = address.split(":");
            var sheetName = parts[0];
            var rowNumber = parts[1];
            excel.useSheet(sheetName);
            var rowIdx = Integer.valueOf(rowNumber);
            var row = excel.getRowAt(--rowIdx);
            var locatorValue = row.getCellText(1);
            e.setLocator(getLocator(locatorValue));
            e.setExcelAddress(sheetName + "&" + rowNumber);
            return e;
        }

        static public List<Elem> all(String address) {
            var excel = new Excel(util.excelPath("locations.xlsx"));
            String[] parts = address.split(":");
            String sheetName = parts[0];
            System.out.println(sheetName);
            excel.useSheet(parts[0]); //page
            String range = parts[1];
            String[] rangeParts = range.split("~");
            Integer startIdx = Integer.valueOf(rangeParts[0]);
            Integer endIdx = Integer.valueOf(rangeParts[1]);
            List<Elem> locators = new ArrayList<>();
            startIdx--;
            endIdx--;
            for (int i = startIdx; i <= endIdx; i++) {
                Elem e = new Elem();
                int rIdx = i + 1;
                String xlsInfo = sheetName + "&" + rIdx;
                System.out.println("xlsInfo> " + xlsInfo);
                e.setExcelAddress(xlsInfo);
                var row = excel.getRowAt(i);
                var locatorValue = row.getCellText(1);
                var locator = getLocator(locatorValue);
                e.setLocator(locator);
                locators.add(e);
            }
            return locators;
        }

        static private By getLocator(String value) {
            By locator;
            boolean isXpath = value.contains("//")
                    || value.contains("/")
                    || value.contains("@");
            if (!isXpath) return locator = css(value);
            else return locator = xp(value);
        }

    }//elem

    static public class user {

        static public void visits(String url) {
            driver.manage().timeouts().pageLoadTimeout(timeLimit);
            driver.get(url);
        }

        static public void openTestSite() {
            ConfigManager config = new ConfigManager();
            String site = config.extract("$.site");
            visits(site);
        }

        static public WebElement findElement(By locator) {
            WebElement element = null;
            ExpectedCondition condition = ExpectedConditions.visibilityOfElementLocated(locator);
            element = (WebElement) fluentWait.until(condition);
            return element;
        }

        static public List<WebElement> findElements(By locator) {
            List<WebElement> elements = null;
            ExpectedCondition condition = ExpectedConditions.presenceOfAllElementsLocatedBy(locator);
            elements = (List<WebElement>) fluentWait.until(condition);
            return elements;
        }

        static public void clicks(By locator) {
            WebElement found =
                    (WebElement) fluentWait.until(ExpectedConditions.elementToBeClickable(locator)
                    );
            found.click();
        }

        static public void clicks(WebElement element) {
            WebElement found =
                    (WebElement) fluentWait.until(
                            ExpectedConditions.elementToBeClickable(element)
                    );
            found.click();
        }

        static public void clicks(Elem elem) {
            String reason = "Could not click the element";
            fluentWait.withMessage(elem.getErrorMessage(reason));
            WebElement found = (WebElement) fluentWait.until(
                    ExpectedConditions.elementToBeClickable(elem.getLocator())
            );
            found.click();
        }

        static public void types(By locator, String text) {
            WebElement element = findElement(locator);
            element = (WebElement) fluentWait.until(ExpectedConditions.visibilityOf(element));
            element.sendKeys(text);
        }

        static public void types(WebElement element, String text) {
            WebElement found = (WebElement) fluentWait.until(ExpectedConditions.visibilityOf(element));
            found.sendKeys(text);
        }

        static public void types(Elem elem, String text) {
            String reason = "Could not type into the element";
            fluentWait.withMessage(elem.getErrorMessage(reason));
            WebElement found = findElement(elem.getLocator());
            found = (WebElement) fluentWait.until(ExpectedConditions.visibilityOf(found));
            found.sendKeys(text);
        }

        static public void highlight(By locator) {
            WebElement element =
                    (WebElement) fluentWait.until(
                            ExpectedConditions.invisibilityOfElementLocated(locator)
                    );
            JavascriptExecutor jse = (JavascriptExecutor) driver;
            String script = "arguments[0].setAttribute('style', 'border: 2px solid purple');";
            jse.executeAsyncScript(script, element);
        }

        static public void switchTab(String title) {
            Set<String> ids = driver.getWindowHandles();
            for (String each : ids) {
                driver.switchTo().window(each);
                String ret = driver.getTitle();
                if (ret.contains(title)) {
                    break;
                }
            }//end::for
        }

        static public String asksTextOf(By locator) {
            WebElement element = findElement(locator);
            WebElement found = (WebElement) fluentWait.until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            );
            return found.getText();
        }
    }//user


    static public class time {
        static public void sleepMili(int milisecond) {
            try {
                Thread.sleep(milisecond);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }

        static public void sleep(int second) {
            try {
                Thread.sleep(second * 1000);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }

        static public void sleepMin(int minute) {
            try {
                long duration = minute * 60 * 1000;
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }
    }//time'


    static public class util {
        static public String excelPath(String file) {
            String root = System.getProperty("user.dir")
                    + "/src/test/resources/excels/";
            return root + file;
        }

        static public WebDriver driverType(String choice, boolean isHeadless) {
            boolean isChrome = choice.equalsIgnoreCase("chrome");
            boolean isChromium = choice.equalsIgnoreCase("chromium");
            boolean isFirefox = choice.equalsIgnoreCase("firefox");
            boolean isEdge = choice.equalsIgnoreCase("edge");
            boolean isSafari = choice.equalsIgnoreCase("safari");

            if (isChrome || isChromium) {
                var options = new ChromeOptions();
                options.addArguments("no-sandbox");
                options.addArguments("--headless");
                options.addArguments("disable-gpu");
                options.addArguments("window-size=1900,1080");
                options.addArguments("window-size=1900,1080");
                WebDriverManager.chromedriver().setup();
                if (isHeadless) return new ChromeDriver(options);
                else return new ChromeDriver();
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
    }//uil

    // 1920, 1080

}//end::class
// https://github.com/dhatim/fastexcel