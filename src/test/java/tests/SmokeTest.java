package tests;

import core.Excel;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import static core.Automation.*;


public class SmokeTest {

    private String path(String file) {
        var path = System.getProperty("user.dir") + "/excels/" + file;
        return path;
    }


    //@Test
    public void demo1() {
        // test date
        var filePath = path("/testData.xlsx");
        Excel excel = new Excel(filePath).useSheet("logins");
        String user1 = excel.dataAt("A2").asString();

        By loc_login = elem.xp("(//a[text()='Log in'])[1]");
        By loc_user = elem.css("#user");

        // Test Step
        browser.openChrome();
        user.visits("https://trello.com");
        user.clicks(loc_login);
        user.types(loc_user, user1);

        time.sleep(10);
        browser.close();
    }


    @Test
    public void test1() {

    }

    @Test
    public void test2() {
        System.out.println("\t\t\ttest 1");
    }

    @Test
    public void test3() {
        System.out.println("\t\t\ttest 1");
    }

    @Test
    public void test4() {
        System.out.println("\t\t\ttest 1");
    }

    @Test
    public void test5() {
        Assert.assertEquals(1, 10);
    }

}//end::class