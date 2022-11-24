package core;
import static core.Automation.*;
import static core.Automation.elem.*;

public class UIAction {


    static public void start(String url) {
        browser.open();
        user.visits(url);
    }

    static public void end() {
        browser.close();
    }

    static public void click(String address) {
        user.clicks(at(address));
    }

    static public void clicksAll(String address) {
        for(Elem e : all(address))
            user.clicks(e);
    }

    static public void type(String address, String text) {
        user.types(at(address), text);
    }

}
