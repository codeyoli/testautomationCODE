package tests;

import org.testng.annotations.Test;
import static core.Automation.*;
import static core.Automation.elem.*;

public class DemoTest {

    @Test
    public void test1() {
        browser.open();
        user.openTestSite();
        String ret = user.asksTextOf(x("//h1"));
        System.out.println("Extracted: " + ret);
        browser.close();
    }
}//end::class
