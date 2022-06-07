package pages;

import org.openqa.selenium.By;

import static utils.Automations.*;

/**
 * This class is page object class for the Trello landing page.
 * It contains list of element that our test script interacts and
 * methods that represent test user's action on this page.
 */
public class HomePage {

    private final String url = "https://trello.com";

    // ---- Web element locations ---- //
    final private By loc_home_header = By.cssSelector("h1");
    final private By loc_home_emailInput = By.xpath("//input[@name='email']");
    final private By loc_home_login = By.cssSelector(".text-primary");
    final private By loc_home_signUpButton = By.xpath("//div/a[2]");


    // ---- User actions ---- //

    /**
     * Opens the test site in browser
     */
    public void open() {
        visit("https://trello.com");
        isVisible(loc_home_header);
    }

    /**
     * Enters email for the signup for free section
     *
     * @param email
     */
    public void enterEmail(String email) {
        isVisible(loc_home_emailInput);
        type(loc_home_emailInput, email);
    }

    /**
     * Clicks the login link from landing page
     */
    public void clickLoginLink() {
        isVisible(loc_home_login);
        click(loc_home_login);
    }

    /**
     * Clicks the account sign up button from the landing page
     */
    public void clickGetTrelloLink() {
        isVisible(loc_home_signUpButton);
        click(loc_home_signUpButton);
    }
}//end::class
