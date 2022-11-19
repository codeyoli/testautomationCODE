package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class Automation {

    @Getter static private WebDriver driver;
    static private FluentWait fluentWait;
    static private final Duration timeLimit = Duration.ofSeconds(20);
    static private final Duration elemTimeLimit = Duration.ofSeconds(5);

    // --- Browser Related --- //
    static public class Browser {
        static public void open() {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            fluentWait = new FluentWait(driver);
            fluentWait.withTimeout(elemTimeLimit);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
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
    }//Browser

    static public class Locator {
        static public By xp(String xpath) {
            return By.xpath(xpath);
        }

        static public By css(String query) {
            return By.cssSelector(query);
        }

        static public By id(String value) {
            return By.id(value);
        }

    }//Locator

    static public class User {

        static public void gotoSite(String url) {
            driver.manage().timeouts().pageLoadTimeout(timeLimit);
            driver.get(url);
        }

        static public WebElement findElement(By locator) {
            WebElement element = null;
            try{
                ExpectedCondition condition = ExpectedConditions.presenceOfElementLocated(locator);
                element = (WebElement) fluentWait.until(condition);
            }catch (NoSuchElementException ex) {

            }catch (TimeoutException timeout) {

            }
            return element;
        }

        static public List<WebElement> findElements(By locator) {
            List<WebElement> elements = null;
            try{
                ExpectedCondition condition = ExpectedConditions.presenceOfAllElementsLocatedBy(locator);
                elements = (List<WebElement>) fluentWait.until(condition);
            }catch (NoSuchElementException ex) {

            }catch (TimeoutException timeout) {

            }
            return elements;
        }

        static public void click(By locator) {
            try{
                WebElement element = findElement(locator);
                element = (WebElement) fluentWait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
            }catch (TimeoutException timeout) {
                System.out.println("Element was not clickable after 10 seconds");
            }
        }

        static public void type(By locator, String text) {
            try{
                WebElement element = findElement(locator);
                element = (WebElement) fluentWait.until(ExpectedConditions.visibilityOf(element));
                element.sendKeys(text);
            }catch (TimeoutException timeout) {

            }
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
    }//User

    static public class Time {
        static public void sleepMili(int milisecond) {
            try{
                Thread.sleep(milisecond);
            }catch (InterruptedException e){
                // DO NOTHING
            }
        }

        static public void sleep(int second) {
            try{
                Thread.sleep(second * 1000);
            }catch (InterruptedException e){
                // DO NOTHING
            }
        }

        static public void sleepMin(int minute) {
            try{
                long duration = minute * 60 * 1000;
                Thread.sleep(duration);
            }catch (InterruptedException e){
                // DO NOTHING
            }
        }
    }//Time
}//end::class


// https://github.com/dhatim/fastexcel