package pages;

import core.TestConfig;
import core.UserActions;

public class Login {

    private UserActions user;

    // ---- Test Data ------ //
    private final String staging = TestConfig.extract("$.site");
    private final String username = TestConfig.extract("$.user.name");
    private final String password = TestConfig.extract("$.user.pass");

    // ---- Element Locations ----//
    private final String input_user_name = "input#username";
    private final String input_user_pass = "input#password";
    private final String login_button = "input#Login";


    public Login(UserActions actions) {
        user = actions;
    }


    public Login openStaging() {
        user.visits(staging);
        return this;
    }

    public Login loginAsAdmin() {

        user.at(input_user_name).types(username);
        user.at(input_user_pass).types(password);
        user.at(login_button).clicks();
        return this;
    }
}