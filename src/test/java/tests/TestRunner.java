package tests;

import core.UiActions;

public abstract class TestRunner {

    protected UiActions user;

    public void startAutomation() {
        user = new UiActions();
        user.opensBrowser();
    }

    public void stopAutomation() {
        user.closesBrowser();
        user = null;
    }
}//end::class