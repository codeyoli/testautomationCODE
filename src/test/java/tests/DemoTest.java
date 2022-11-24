package tests;

import org.testng.annotations.Test;
import static core.UIAction.*;

public class DemoTest {


    @Test
    public void test2() {
        start("https://trello.com");
        clicksAll("home:3~7");
        end();
    }
}//end::class
