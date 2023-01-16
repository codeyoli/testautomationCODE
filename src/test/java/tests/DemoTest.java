package tests;

import org.testng.annotations.Test;
import static core.UIAction.*;
import static core.Automation.*;

public class DemoTest {

    @Test
    public void codeTestGround() {
        start("https://trello.com");
        click("home:2");
        time.sleep(10);
        end();
    }
}//end::class