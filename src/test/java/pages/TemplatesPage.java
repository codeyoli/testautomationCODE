package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static utils.Automations.*;

public class TemplatesPage {

    // ---- Web element locations ---- //
    final private By loc_features_tab = By.xpath("(//button[@data-testid='bignav-tab'])[1]");
    final private By loc_templates_button = By.xpath("(//div/p[text()='Templates'])[1]");
    final private By loc_page_banner = By.xpath("//h1[text()='Templates for Trello']");
    final private By loc_find_template = By.xpath("//div[text()='Find template']");

    // ---- User actions ---- //
    public boolean open() {
        click(loc_features_tab);
        click(loc_templates_button);
        boolean result = isVisible(loc_page_banner);
        return result;
    }

    public void searchForTemplate(String name) {
        highlight(loc_find_template);
        type(loc_find_template, name);
        type(loc_find_template, Keys.ENTER.toString());
    }
}
