package tests;

import core.UserActions;
import pages.Home;
import pages.Login;
import pages.SetUp;

public abstract class TestRunner {

    protected UserActions user;
    protected Home homePage;
    protected Login loginPage;
    protected SetUp setupPage;

    public void startAutomation() {
        user = new UserActions();
        user.opensBrowser();
        homePage = new Home(user);
        loginPage = new Login(user);
        setupPage = new SetUp(user);
    }

    public void stopAutomation() {
        user.closesBrowser();
        user = null;
    }

}//end::class