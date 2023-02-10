package tests;

import core.ConfigManager;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import static core.Automation.*;

public class DemoTest {

    private final String yearup_staging = new ConfigManager().extract("$.site");
    private final String username = new ConfigManager().extract("$.user.name");
    private final String password = new ConfigManager().extract("$.user.pass");

    @Test
    public void demoTest() {

        By user_name = $("input#username");
        By user_pass = $("input#password");
        By login_button = $("input#Login");
        By gear_icon = $("(//ul[@class='slds-global-actions']//li)[6]");
        By setup_button = $("li#related_setup_app_home");

        // Set Up page related
        By quick_find = $("[placeholder='Quick Find']");
        By report_types = $("//mark[text()='Report Types']");
        By page_header = $(".noSecondHeader");
        By continue_button = $("input[name='save']");


        browser.open();
        browser.maxmize();
        user.visits(yearup_staging);
        user.types(user_name, username);
        user.types(user_pass, password);
        user.clicks(login_button);
        user.clicks(gear_icon);
        user.clicks(setup_button);

        user.opensTabWithUrl("SetupOneHome");
        user.types(quick_find, "Report Types");
        user.clicks(report_types);
        user.changeToFrame();
        user.canSee(page_header);
        browser.close();
    }
}//end::class