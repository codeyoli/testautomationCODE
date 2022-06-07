package pages;

import org.openqa.selenium.By;
import static utils.Automations.*;

/**
 * This class is page object class for the Trello login page.
 * It contains list of element that our test script interacts and
 * methods that represent test user's action on this page.
 */
public class LoginPage {

    // ---- Web element locations ---- //
    private final By loc_email = By.cssSelector("input#user");
    private final By loc_password = By.cssSelector("input#password");
    private final By loc_login_button = By.cssSelector("input#login");
    private final By loc_error_message = By.cssSelector("div#error > p");


    // ---- User actions ---- //

    /**
     * Attemps sign in to the trello with provided username
     * and password.
     *
     * @param username String
     * @param password String
     */
    public void signInWith(String username, String password) {
        type(loc_email, username);
        highlight(loc_email);
        type(loc_password, password);
        highlight(loc_password);
        click(loc_login_button);
    }

    /**
     * Returns the content of global error element.
     *
     * @return String
     */
    public String getErrorMessage() {
        String content = getText(loc_error_message);
        return content;
    }
}//end::class