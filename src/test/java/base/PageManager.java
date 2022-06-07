package base;

import pages.HomePage;
import pages.LoginPage;
import pages.TemplatesPage;

/**
 * This class is provides each page objects in the project. Make sure to
 * register newly created page object to this page.
 */
public class PageManager {

    // ---- page object references ---- //
    private HomePage homePage;
    private LoginPage loginPage;
    private TemplatesPage templatesPage;


    // ---- methods for page object access  ---- //

    /**
     * Use this method to get access to the home page object
     * anywhere inside your test case.
     *
     * @return HomePage object
     */
    public HomePage home() {
        boolean homePageIsNeverUsed = (homePage == null);
        if(homePageIsNeverUsed) {
            homePage = new HomePage();
            return homePage;
        }

        // it was used before, returned the
        // previously used home page object
        return homePage;
    }

    /**
     * Use this method to get access to the login page object
     * anywhere inside your test case.
     *
     * @return LoginPage object
     */
    public LoginPage login() {
        boolean loginPageIsNeverUsed = (loginPage == null);
        if(loginPageIsNeverUsed) {
            loginPage = new LoginPage();
            return loginPage;
        }
        // it was used before, returned the
        // previously used home page object
        return loginPage;
    }

    /**
     * Use this method to get access to the templates page object
     * anywhere inside your test case.
     *
     * @return TemplatesPage object
     */
    protected TemplatesPage template() {
        boolean templatesPageIsNeverUsed = (templatesPage == null);
        if(templatesPageIsNeverUsed) {
            templatesPage = new TemplatesPage();
            return templatesPage;
        }
        // it was used before, returned the
        // previously used home page object
        return templatesPage;
    }
}//end::class
