package tests;

import core.Element;
import org.testng.annotations.Test;
import static core.Automation.*;
import static core.Automation.elem.*;

public class DemoTest {

    @Test
    public void locationTest() {
        browser.open();
        user.visits("https://trello.com");

        for(Element e :  all("home:3~7")) {
            user.clicks(e);
        }
        browser.close();
    }

}//end::class
