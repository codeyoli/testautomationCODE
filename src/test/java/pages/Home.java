package pages;

import org.openqa.selenium.By;

import static core.Automation.*;



public class Home {

    By gear_icon = $("(//ul[@class='slds-global-actions']//li)[6]");
    By setup_button = $("li#related_setup_app_home");

    public void visitSetUpPage() {
        user.clicks(gear_icon);
        user.clicks(setup_button);
        user.opensTabWithUrl("SetupOneHome");
    }

}