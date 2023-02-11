package tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.Pages;
import static core.Automation.*;


@Listeners(core.TestDetection.class)
public class DemoTest {

    @Test
    public void demo_test_case_1(){

        browser.openWithDocker();
        Pages.login.openStaging()
                .loginAsAdmin();

        Pages.home.visitSetUpPage();

        Pages.setUp.searchFor("Report Types")
                .openAllReportTypes().withInitial("S")
                .selectReportOf("SUPER_REPORT_TYPE_Contact")
                .thenPreviewLayout()
                .verifyLayoutPageContacts();

        browser.close();
    }


    @Test()
    public void retry_sample_test() {

        browser.open();
        user.visits("https://trello.com");
        user.clicks($("[class='Buttonsstyles__Button-sc-1jwidxo-0 kTwZBr']"));
        user.types($("input#user"), "ahotmanager@gmail.com");
        String actual = user.asksTextOf($("input#login"));
        browser.close();

        Assert.assertEquals(actual, "Nijat");
    }

}//end::class