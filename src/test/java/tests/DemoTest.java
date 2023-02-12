package tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

//@Listeners(core.TestDetection.class)
public class DemoTest extends TestRunner {


    @Test
    public void test_new_stuff() {

        // --- Element Locations --- //
        String computer_tab = "//ul[@class='top-menu notmobile']/li/a[@href='/computers']";
        String electronics_tab = "//ul[@class='top-menu notmobile']/li/a[@href='/electronics']";
        String login_button = ".ico-login";
        String email_input = "input#Email";
        String pass_input = "input#Password";
        String sign_in = "[class='button-1 login-button']";

        // --- Test Script ---- //
        startAutomation();
        user.visits("https://demo.nopcommerce.com/");
        user.at(computer_tab).clicks();
        user.pauseForSec(2);
        user.at(electronics_tab).clicks();
        user.pauseForSec(2);
        user.at(login_button).clicks();
        user.at(email_input).types("tester@gmail.com");
        user.pauseForSec(2);
        user.at(pass_input).types("StrongPass123!");
        user.at(sign_in).clicks();
        stopAutomation();
    }

}//end::class