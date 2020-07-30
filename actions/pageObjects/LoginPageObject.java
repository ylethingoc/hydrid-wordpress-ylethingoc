package pageObjects;

import commons.AbstractPage;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import pageUI.LoginPageUI;

public class LoginPageObject extends AbstractPage {
    WebDriver driver;

    public LoginPageObject(WebDriver mappingDriver) {
        driver = mappingDriver;
    }

    public String getLoginPageUrl() {
        return getCurrentUrl(driver);
    }
    public void clickToLoginContinueButton() {
        clickToElement(driver, LoginPageUI.CONT_AND_LOGIN_BUTTON);
    }

    public String getEmailErrorMessage() {
        return getElementText(driver, LoginPageUI.ERROR_MESSAGE);
    }

    public void inputToEmailTextbox(String email, String dynamicValue) {
        sendTextToElement(driver, LoginPageUI.DYNAMIC_TEXTBOX, email, dynamicValue);
    }

    public void inputToEmailTextbox(String email) {
        sendTextToElement(driver, LoginPageUI.EMAIL_TEXTBOX, email);
    }

    public void inputToPasswordTextbox(String password) {
        sendTextToElement(driver, LoginPageUI.PASSWORD_TEXTBOX, password);
    }

    public void waitForPassWordTextboxtVisible(String dynamicValue) {
        waitForElementVisible(driver, LoginPageUI.DYNAMIC_TEXTBOX, dynamicValue);
    }

    public void waitForErrorMessageVisible() {
        waitForElementVisible(driver, LoginPageUI.ERROR_MESSAGE);
    }

    public DashBoardPageObject clickToLoginButton() {
        waitForElementVisible(driver, LoginPageUI.CONT_AND_LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.CONT_AND_LOGIN_BUTTON);
        return PageGeneratorManager.getDashBroadPage(driver);
    }

}


