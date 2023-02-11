package pages;

import core.TestConfig;
import lombok.extern.java.Log;
import org.openqa.selenium.By;
import static core.Automation.*;

public class Login {

    // ---- Test Data ------ //
    private final String staging = TestConfig.extract("$.site");
    private final String username = TestConfig.extract("$.user.name");
    private final String password = TestConfig.extract("$.user.pass");

    // ---- Element Locations ----//
    static public final By user_name = $("input#username");
    static public final By user_pass = $("input#password");
    static public final By login_button = $("input#Login");


    public Login openStaging() {
        user.visits(staging);
        return this;
    }

    public Login loginAsAdmin() {
        user.types(user_name, username);
        user.types(user_pass, password);
        user.clicks(login_button);
        return this;
    }
}