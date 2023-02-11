package pages;

import org.openqa.selenium.By;

import static core.Automation.*;



public class SetUp {

    // --------- Element Location --------- //
    private final By quick_find = $("[placeholder='Quick Find']");
    private final By report_types = $("//mark[text()='Report Types']");
    private final By page_header = $(".noSecondHeader");
    private final By continue_button = $("input[name='save']");
    private final By preview_layout = $("input[name='previewLayout']");


    // ----------- Methods ---------------//
    public SetUp searchFor(String query) {
        user.types(quick_find, query);
        return this;
    }

    public SetUp openAllReportTypes() {
        user.clicks(report_types);
        user.changeToFrame();
        user.canSee(page_header);
        user.clicks(continue_button);
        return this;
    }

    public SetUp withInitial(String initial) {
        By initialElem = $("//div[4]//span[text()='"+initial+"']");
        user.changeToFrame();
        user.clicks(initialElem);
        return this;
    }


    public SetUp selectReportOf(String title) {
        By chosen_report = $("//th/a[text()='"+title+"']");
        user.changeToFrame();
        user.clicks(chosen_report);
        return this;
    }

    public SetUp thenPreviewLayout() {
        user.changeToFrame();
        user.clicks(preview_layout);
        return this;
    }

    public SetUp verifyLayoutPageContacts(String ...contacts) {
        By ask_amount_2015 = $("//label[text()='2015 Ask Amount']");
        user.opensTabWithUrl("analytics");
        user.highlight(ask_amount_2015);
        return this;
    }
}//end::class