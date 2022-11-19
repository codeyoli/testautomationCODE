package tests;

import core.Excel;
import org.testng.annotations.Test;


public class SmokeTest {


    private String path(String file) {
        var path = System.getProperty("user.dir") + "/excel/" + file;
        return path;
    }
    private void print(String value) {
        System.out.println(value);
    }


    @Test
    public void demo1() {
        var path = path("testData.xlsx");
        var excel = new Excel(path);
        excel.useSheet("credentials");
        print(excel.toString());

    }


    @Test
    public void test1() {
        var filePath = path("testData.xlsx");
        var excel = new Excel(filePath);
        excel.useSheet("credentials");

        System.out.println();

        excel.useSheet("flags");
        System.out.println(excel);

        excel.useSheet("sites");
        System.out.println(excel);
    }

}//end::class
