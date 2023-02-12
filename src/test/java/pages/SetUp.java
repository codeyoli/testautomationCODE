package pages;

import core.UserActions;
import org.openqa.selenium.By;

import static core.Automation.*;



public class SetUp {

    private UserActions user;

    // --------- Element Location --------- //
    private final String quick_find = "[placeholder='Quick Find']";
    private final String report_types = "//mark[text()='Report Types']";
    private final String page_header = ".noSecondHeader";
    private final String continue_button = "input[name='save']";
    private final String preview_layout = "input[name='previewLayout']";


    public SetUp(UserActions actions) {
        user = actions;
    }


    // ----------- Methods ---------------//
    public SetUp searchFor(String query) {
        user.at(quick_find).types(query);
        return this;
    }

    public SetUp openAllReportTypes() {
        user.at(report_types).clicks();
        user.changeToFrame();
        user.at(page_header).canSeeIt();
        user.at(continue_button).clicks();
        return this;
    }

    public SetUp withInitial(String initial) {
        String initialElem = "//div[4]//span[text()='"+initial+"']";
        user.changeToFrame();
        user.at(initialElem).clicks();
        return this;
    }


    public SetUp selectReportOf(String title) {
        String chosen_report = "//th/a[text()='"+title+"']";
        user.changeToFrame();
        user.at(chosen_report).clicks();
        return this;
    }

    public SetUp thenPreviewLayout() {
        user.changeToFrame();
        user.at(preview_layout).clicks();
        return this;
    }

    public SetUp verifyLayoutPageContacts(String ...contacts) {
        String ask_amount_2015 = "//label[text()='2015 Ask Amount']";
        user.opensTabWithUrl("analytics");
        user.at(ask_amount_2015).highlightIt();
        return this;
    }
}//end::class