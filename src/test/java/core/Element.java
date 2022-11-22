package core;

import lombok.Data;
import org.openqa.selenium.By;

public @Data class Element {
    private By locator;
    private String excelAddress;

    public String getSheet() {
        return excelAddress.split("&")[0];
    }

    public String getRowIndex() {
        return excelAddress.split("&")[1];
    }
}
