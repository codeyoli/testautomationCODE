package tests;

import core.UserActions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(core.TestDetection.class)
public class ExperimentTest extends TestRunner {

    @Test
    public void demo_test_case_1() {

        startAutomation();
        loginPage.openStaging()
                .loginAsAdmin();

        homePage.visitSetUpPage();

        setupPage.searchFor("Report Types")
                .openAllReportTypes().withInitial("S")
                .selectReportOf("SUPER_REPORT_TYPE_Contact")
                .thenPreviewLayout()
                .verifyLayoutPageContacts();

        stopAutomation();
    }

    @Test
    public void test_new_stuff() {

        UserActions user = new UserActions();

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
        user.at(electronics_tab).clicks();
        user.at(login_button).clicks();
        user.at(email_input).types("tester@gmail.com");
        user.at(pass_input).types("StrongPass123!");
        user.at(sign_in).clicks();
        stopAutomation();
    }
}