package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

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

    static public class UI {
        static public void gotoSite(String url) {
            driver.manage().timeouts().pageLoadTimeout(timeLimit);
            driver.get(url);
        }

        static public void click(By locator) {
            fluentWait.withTimeout(elemTimeLimit);
            fluentWait.withMessage("Element not clickable");
            fluentWait.withMessage("Element location: "+ locator.toString());
            try{
                WebElement elem = (WebElement)fluentWait.until(ExpectedConditions.elementToBeClickable(locator));
                elem.click();
            }catch (TimeoutException timeout) {
                System.out.println("Element was not clickable after 10 seconds");
            }
        }
    }//UI

    static public class Time {
        static public void sleep(int second) {
            try{
                Thread.sleep(second * 1000);
            }catch (InterruptedException e){
                // DO NOTHING
            }
        }
    }//Time


}//end::class
