package core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UiActions {

    private final Duration timeLimit = Duration.ofSeconds(5);
    // --------- Fields ----------//
    private WebDriver driver;
    private WebDriverWait waits;
    private By locator;

    // --------- Browser Related ----------//
    private WebDriverManager getWebDriverManager(String type) {
        if (type.equalsIgnoreCase("chrome")) {
            return WebDriverManager.chromedriver();
        } else if (type.equalsIgnoreCase("firefox")) {
            return WebDriverManager.firefoxdriver();
        } else if (type.equalsIgnoreCase("safari")) {
            return WebDriverManager.safaridriver();
        } else if (type.equalsIgnoreCase("edge")) {
            return WebDriverManager.edgedriver();
        } else {
            // default
            return WebDriverManager.chromedriver();
        }
    }

    public void opensBrowser() {
        String choice = TestConfig.extract("$.browser.choice");
        boolean video = TestConfig.extract("$.browser.video");
        boolean vnc = TestConfig.extract("$.browser.vnc");
        System.out.println("Chosen browser is    : " + choice);
        System.out.println("Video choice selected: " + video);
        System.out.println("VNC choice selected  : " + vnc);

        if (video) {
            // docker environment
            WebDriverManager manager = getWebDriverManager(choice);
            manager.browserInDocker().dockerScreenResolution("1920x1080x24");
            manager.enableRecording().dockerRecordingOutput(Automation.util.root() + "/video/");
            if (vnc) {
                manager.enableVnc();
            }
            String videoName = "TestCase";
            if(TestDetection.currentTestCaseName != null) {
                videoName = TestDetection.currentTestCaseName + "__";
            }
            manager.config().setDockerRecordingPrefix(videoName);
            driver = manager.create();
            waits = new WebDriverWait(driver, timeLimit);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.manage().window().setSize(new Dimension(1920, 1080));
        } else {
            // normal environment
            WebDriverManager manager = getWebDriverManager(choice);
            driver = manager.create();
            waits = new WebDriverWait(driver, timeLimit);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        }
    }

    public void closesBrowser() {
        if (driver == null) return;
        driver.quit();
    }

    public void refreshesPage() {
        WebDriver.Navigation navigationPanel = driver.navigate();
        navigationPanel.refresh();
    }

    public void pressRightArrow() {
        WebDriver.Navigation navigationPanel = driver.navigate();
        navigationPanel.forward();
    }

    public void pressLeftArrow() {
        WebDriver.Navigation navigationPanel = driver.navigate();
        navigationPanel.back();
    }

    public void acceptPopUp() {
        Alert alert = waits.until(ExpectedConditions.alertIsPresent());
        alert.accept();
    }

    public void dismissPopUp() {
        Alert alert = waits.until(ExpectedConditions.alertIsPresent());
        alert.dismiss();
    }

    public String asksForUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    public String asksForTabTitle() {
        String title = driver.getTitle();
        return title;
    }

    // ------ Element Locators & Transitions --------- //
    public UiActions at(String query) {
        if (query.contains("//") || query.contains("@")) {
            locator = By.xpath(query);
        } else {
            locator = By.cssSelector(query);
        }
        return this;
    }

    public UiActions thenAt(String query) {
        if (query.contains("//") || query.contains("@")) {
            locator = By.xpath(query);
        } else {
            locator = By.cssSelector(query);
        }
        return this;
    }

    public UiActions and() {
        return this;
    }


    // --------- User Action Related ----------//
    public void visits(String url) {
        driver.manage().timeouts().pageLoadTimeout(timeLimit);
        driver.get(url);
    }

    private WebElement findsElement(By locator) {
        WebElement elem;
        try {
            elem = waits.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (StaleElementReferenceException error) {
            waits.withMessage("Could not find the element -> " + locator.toString());
            elem = waits.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        return elem;
    }

    private List<WebElement> findsElements(By locator) {
        List<WebElement> elems = waits.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        return elems;
    }

    public void clicks() {
        WebElement found = findsElement(locator);
        found = waits.until(ExpectedConditions.elementToBeClickable(found));
        found.click();
    }

    public void types(String text) {
        WebElement found = findsElement(locator);
        found.sendKeys(text);
    }

    public void canSeeIt() {
        WebElement element = findsElement(locator);
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        String errMsg = "Could not see the element of: " + locator.toString();
        Assert.assertTrue(element.isDisplayed(), errMsg);
    }

    public void checksTextIs(String expected) {
        String errMsg = "Text mismatch of: " + locator.toString();
        WebElement element = findsElement(locator);
        String text = element.getText();
        Assert.assertEquals(expected, text, errMsg);
    }

    public void checksTextContains(String subtext) {
        String errMsg = "Text mismatch of: " + locator.toString();
        WebElement element = findsElement(locator);
        String text = element.getText();
        Assert.assertTrue(text.contains(subtext), errMsg);
    }

    public UiActions highlightIt() {
        WebElement found = findsElement(locator);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String script = "arguments[0].setAttribute('style', 'border: 2px solid purple');";
        jse.executeScript(script, found);
        return this;
    }

    public String asksForText() {
        WebElement found = findsElement(locator);
        return found.getText();
    }

    public List<String> asksForAllTexts() {
        List<WebElement> found = findsElements(locator);
        List<String> texts = new ArrayList<>();
        for (WebElement e : found) {
            texts.add(e.getText());
        }
        return texts;
    }

    public UiActions pauseForSec(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            // DO NOTHING
        }
        return this;
    }

    public UiActions pauseForMin(int minute) {
        try {
            long duration = (long) minute * 60 * 1000;
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            // DO NOTHING
        }
        return this;
    }

    public void opensTabWith(String title) {
        Set<String> ids = driver.getWindowHandles();
        for (String each : ids) {
            driver.switchTo().window(each);
            String ret = driver.getTitle();
            if (ret.contains(title)) {
                break;
            }
        }//end::for
    }

    public void opensTabWithUrl(String urlPart) {
        Set<String> ids = driver.getWindowHandles();
        for (String each : ids) {
            driver.switchTo().window(each);
            String wholeUrl = driver.getCurrentUrl();
            boolean isPart = wholeUrl.contains(urlPart);
            if (isPart) break;
        }//end::for
    }

    public void changeToFrame() {
        Automation.time.pauseSec(2);
        By loc_iframe = By.xpath("//iframe");
        WebElement frame = findsElement(loc_iframe);
        if (frame.isDisplayed()) {
            driver.switchTo().defaultContent();
            driver.switchTo().frame(frame);
        }
    }

    public void changeToMainPage() {
        driver = driver.switchTo().defaultContent();
    }
}