package tests;

import actions.HomeAction;
import core.Elem;
import org.testng.annotations.Test;

import static core.Automation.*;
import static core.Automation.elem.*;

public class DemoTest {

    @Test
    public void locationTest() {
        browser.open();
        user.visits("https://trello.com");

        for (Elem e : all("home:3~7")) {
            user.clicks(e);
        }
        browser.close();
    }


    @Test(
     groups = {"smoke"}
    )
    public void fawadDemo() {
        browser.open();

        new HomeAction()
                .openApp()
                .tapEachTabs()
                .visitLoginPage();
        browser.close();
    }

    @Test(groups = {"dev", "smoke"})
    public void testCase2() {
        System.out.println("test case 2");
    }


    @Test(groups = {"dev", "reg"})
    public void testCase3() {
        System.out.println("test case 3");
    }


    @Test(groups = {"e2e"})
    public void testCase4() {
        System.out.println("test case fawad");
    }

}//end::class
