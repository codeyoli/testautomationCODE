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

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Automation {

    static private final Duration timeLimit = Duration.ofSeconds(20);
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

        static public void open() {
            ConfigManager config = new ConfigManager();
            String browserChoice = config.extract("$.browser.choice");
            boolean isHeadless = config.extract("$.browser.headless");
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


    static public class elem {

        static public By x(String xpath) {
            return By.xpath(xpath);
        }

        static public By s(String query) {
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
            if (!isXpath) return locator = s(value);
            else return locator = x(value);
        }

    }//elem

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
            windowHandle = driver.getWindowHandle();
            WebElement frame = findsElement($("//iframe"));
            driver = driver.switchTo().frame(frame);
        }

        static public void changeToMainPage() {
            driver = driver.switchTo().window(windowHandle);
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
        static public void sleepMili(int milisecond) {
            try {
                Thread.sleep(milisecond);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }

        static public void sleep(int second) {
            try {
                Thread.sleep(second * 1000L);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }

        static public void sleepMin(int minute) {
            try {
                long duration = (long) minute * 60 * 1000;
                Thread.sleep(duration);
            } catch (InterruptedException e) {
                // DO NOTHING
            }
        }
    }//time

    static public class util {
        static public String excelPath(String file) {
            String root = System.getProperty("user.dir")
                    + "/excels/";
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
    }//uil

}//end::class
// https://github.com/dhatim/fastexcel