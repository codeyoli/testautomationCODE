package actions;

import core.Elem;

import static core.Automation.*;
import static core.Automation.elem.*;

public class HomeAction {

    public HomeAction openApp() {
        user.visits("https://trello.com");
        return this;
    }

    public HomeAction visitLoginPage() {
        user.clicks(at("home:2:login button"));
        return this;
    }

    public HomeAction tapEachTabs() {
        for(Elem e : all("home:3~7"))
            user.clicks(e);
        return this;
    }
}
