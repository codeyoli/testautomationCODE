package tests;

import core.Automation;
import core.UiActions;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import pages.Pages;

import static core.Automation.$;

public class ExperimentTest {

    @Test
    public void demo_test_case_1() {

        Automation.browser.openWithDocker();
        Pages.login.openStaging()
                .loginAsAdmin();

        Pages.home.visitSetUpPage();

        Pages.setUp.searchFor("Report Types")
                .openAllReportTypes().withInitial("S")
                .selectReportOf("SUPER_REPORT_TYPE_Contact")
                .thenPreviewLayout()
                .verifyLayoutPageContacts();

        Automation.browser.close();
    }


    @Test()
    public void retry_sample_test() {

        By computer_tab = $("//ul[@class='top-menu notmobile']/li/a[@href='/computers']");
        By electronics_tab = $("//ul[@class='top-menu notmobile']/li/a[@href='/electronics']");
        By login_button = $(".ico-login");
        By email_input = $("input#Email");
        By pass_input = $("input#Password");
        By sign_in = $("[class='button-1 login-button']");

        Automation.browser.openBrowser();
        Automation.user.visits("https://demo.nopcommerce.com/");
        Automation.user.clicks(computer_tab);
        Automation.user.clicks(electronics_tab);
        Automation.user.clicks(login_button);
        Automation.user.types(email_input, "tester@gamil.com");
        Automation.user.types(pass_input, "StrongPass123!");
        Automation.user.clicks(sign_in);
        Automation.browser.close();
    }


    @Test
    public void test_new_stuff() {

        UiActions user = new UiActions();

        // --- Element Locations --- //
        String computer_tab = "//ul[@class='top-menu notmobile']/li/a[@href='/computers']";
        String electronics_tab = "//ul[@class='top-menu notmobile']/li/a[@href='/electronics']";
        String login_button = ".ico-login";
        String email_input = "input#Email";
        String pass_input = "input#Password";
        String sign_in = "[class='button-1 login-button']";

        // --- Test Script ---- //
        user.opensBrowser();
        user.visits("https://demo.nopcommerce.com/");
        user.at(computer_tab).clicks();
        user.at(electronics_tab).clicks();
        user.at(login_button).clicks();
        user.at(email_input).types("tester@gmail.com");
        user.at(pass_input).types("StrongPass123!");
        user.at(sign_in).clicks();
        user.closesBrowser();
    }

}
