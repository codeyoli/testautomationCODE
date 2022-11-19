package tests;

import org.testng.annotations.Test;
import static utils.Automation.*;

public class RegressionTest {


    @Test
    public void demo() {
        Browser.open();
        UI.gotoSite("https://alphaleaf.tech/");
        UI.click(Locator.xp("//button[@id='nijat']"));
        Browser.close();
    }
}
