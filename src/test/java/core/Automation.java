package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Automation {

    static private final Duration timeLimit = Duration.ofSeconds(20);
    static private final Duration elemTimeLimit = Duration.ofSeconds(5);
    @Getter
    static private WebDriver driver;
    static private FluentWait fluentWait;

    // --- browser Related --- //
    static public class browser {
        static public void open() {
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
            boolean isXpath =  value.contains("//")
                    || value.contains("/")
                    || value.contains("@");
            if(!isXpath) return locator = css(value);
            else return locator = xp(value);
        }

    }//elem



    static public class user {

        static public void visits(String url) {
            driver.manage().timeouts().pageLoadTimeout(timeLimit);
            driver.get(url);
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
                    (WebElement)fluentWait.until(
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
    }//uil


}//end::class
// https://github.com/dhatim/fastexcel