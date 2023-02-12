package pages;

import core.UserActions;

public class Home {

    private UserActions user;
    private final String gear_icon = "(//ul[@class='slds-global-actions']//li)[6]";
    private final String setup_button = "li#related_setup_app_home";


    public Home(UserActions actions) {
        user = actions;
    }

    public void visitSetUpPage() {
        user.at(gear_icon).clicks();
        user.at(setup_button).clicks();
        user.opensTabWithUrl("SetupOneHome");
    }
}