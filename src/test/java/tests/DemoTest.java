package tests;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import core.utils.Logs;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.io.File;


@Listeners({ReportPortalTestNGListener.class, core.TestDetection.class})
public class DemoTest extends TestRunner {

    @Test
    public void test_new_stuff() {

        // --- Element Locations --- //
        String computer_tab = "//ul[@class='top-menu notmobile']/li/a[@href='/computers']";
        String electronics_tab = "//ul[@class='top-menu notmobile']/li/a[@href='/electronics']";
        String login_button = ".ico-login";
        String email_input = "input#Email";
        String pass_input = "input#Password";
        String sign_in = "[class='button-1 login-button']";

        // --- Test Script ---- //
        startAutomation();
        user.visits("https://demo.nopcommerce.com/");
        user.at(computer_tab).clicks();
        user.pauseForSec(2);
        user.at(electronics_tab).clicks();
        user.pauseForSec(2);
        user.at(login_button).clicks();
        user.at(email_input).types("tester@gmail.com");
        user.pauseForSec(2);
        user.at(pass_input).types("StrongPass123!");
        user.at(sign_in).clicks();
        stopAutomation();
    }


    @Test
    public void file_upload_test() {
        startAutomation();
        String path = System.getProperty("user.dir") + "/year_up_test_case_demo_run.mp4";
        File video = new File(path);
        Logs._RPFILE(video, "Recorded video");
        stopAutomation();
    }

}//end::class