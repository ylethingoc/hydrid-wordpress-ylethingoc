package commons;

import org.openqa.selenium.WebDriver;

import pageObjects.DashBoardPageObject;
import pageObjects.LoginPageObject;

public class PageGeneratorManager {
    public static LoginPageObject getLoginPage(WebDriver driver) {
        return new LoginPageObject(driver);
    }
    public static DashBoardPageObject getDashBroadPage(WebDriver driver) {
        return new DashBoardPageObject(driver);
    }
}
