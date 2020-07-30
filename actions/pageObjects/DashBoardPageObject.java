package pageObjects;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUI.DashboardPageUI;

public class DashBoardPageObject extends AbstractPage {
    WebDriver driver;

    public DashBoardPageObject(WebDriver mappingDriver) {
        driver = mappingDriver;
    }

    public String getHeaderText() {
        return getElementText(driver, DashboardPageUI.DASHBOARD_HEADER);
    }

    public void waitForDashBoardHeaderVisible() {
        waitForElementVisible(driver, DashboardPageUI.DASHBOARD_HEADER);
    }

    public void clickToAccountImage() {
        clickToElement(driver, DashboardPageUI.ACCOUNT_IMAGE);
    }

    public void clickToLogOutButton() {
        clickToElement(driver, DashboardPageUI.LOGOUT_BUTTON);
    }

    public void elementIsDisplay() {
        isElementDisplay(driver, DashboardPageUI.GET_START_TEXT);
    }
}
