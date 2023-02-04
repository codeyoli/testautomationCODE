package tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import static core.UIAction.*;

@Listeners(ReportPortalTestNGListener.class)
public class DemoTest {

    @Test
    public void test2() {
        start("https://trello.com");
        clicksAll("home:3~7");
        end();
    }
}//end::class