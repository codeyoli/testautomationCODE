package core;

import lombok.Data;
import org.openqa.selenium.By;

public @Data class Elem {

    // -- Fields -- //
    private By locator;
    private String excelAddress;


    // -- Constructor -- //
    public Elem(){}

    public Elem(By by){
        this.locator = by;
    }


    // -- Methods -- //

    public String getSheet() {
        return excelAddress.split("&")[0];
    }

    public String getRowIndex() {
        return excelAddress.split("&")[1];
    }

    public String getErrorMessage(String problem) {
        StringBuilder sb = new StringBuilder();
        sb.append(problem + " specified at sheet: ")
                .append(getSheet() + " ")
                .append("at Row: " + getRowIndex());
        return sb.toString();
    }
}//end::class
