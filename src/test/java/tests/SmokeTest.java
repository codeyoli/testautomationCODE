package tests;

import base.TestBase;
import org.openqa.selenium.By;
import org.testng.annotations.*;

import static utils.DriverUtil.*;
import static utils.Automations.*;


@Listeners(utils.TestDetector.class)
public class SmokeTest extends TestBase {



    @Test
    public void verifyAppIsAccessible() {
        //----Test Data
        String testSite = "http://webdriveruniversity.com/index.html";
        By loc_contact = By.cssSelector("#contact-us h1");
        By loc_firstname_input = By.cssSelector("input[name='first_name']");

        //----Test Steps
        visit(testSite);
        click(loc_contact);
        switchToTab("Contact Us");
        type(loc_firstname_input, "Murazzim");
        closeWindow();
        switchToTab("WebDriver");
    }


    @Test
    public void userCanSearchForTemplate() {
        String testSite ="https://trello.com";

        visit(testSite);

    }
}
