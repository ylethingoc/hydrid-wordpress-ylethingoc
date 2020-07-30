package org.wordpress.login;

import commons.AbstractTest;
import commons.PageGeneratorManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.DashBoardPageObject;
import pageObjects.LoginPageObject;


public class ValidateLoginUsingMultiBrowser extends AbstractTest {
    WebDriver driver;
    LoginPageObject loginPage;
    String loginPageUrl;
    DashBoardPageObject dashBoardPage;


    @BeforeClass
    @Parameters("browser")
    public void BeforeClass(String browserName) {
        driver = getBrowserDriver(browserName);
        loginPage = PageGeneratorManager.getLoginPage(driver);
        loginPageUrl = loginPage.getLoginPageUrl();

    }

    @BeforeMethod
    public void BeforeMethod() {
        driver.get(loginPageUrl);
    }

    @Test
    public void TC01_LoginWithEmptyEmailAndPassword() {
        loginPage.clickToLoginContinueButton();
        loginPage.waitForErrorMessageVisible();
        Assert.assertEquals(loginPage.getEmailErrorMessage(),
                "Please enter a username or email address.");

    }

    @Test
    public void TC02_LoginWithInvalidEmail() {
        loginPage.inputToEmailTextbox("123456@12345");
        loginPage.clickToLoginContinueButton();
        loginPage.waitForErrorMessageVisible();
        Assert.assertEquals(loginPage.getEmailErrorMessage(),
                "User does not exist. Would you like to create a new account?");
    }

    @Test
    public void TC03_LoginWithIncorrectEmail() {
        loginPage.inputToEmailTextbox("123@123.com");
        loginPage.clickToLoginContinueButton();
        loginPage.waitForErrorMessageVisible();
        Assert.assertEquals(loginPage.getEmailErrorMessage(),
                "Please log in using your WordPress.com username instead of your email address.");
    }

    @Test
    public void TC04_LoginWithEmptyPassword() {
        loginPage.inputToEmailTextbox("automationeditor", "usernameOrEmail");
        loginPage.clickToLoginContinueButton();
        loginPage.waitForPassWordTextboxtVisible("password");
        loginPage.clickToLoginContinueButton();
        loginPage.waitForErrorMessageVisible();
        Assert.assertEquals(loginPage.getEmailErrorMessage(),
                "Don't forget to enter your password.");
    }

    @Test
    public void TC05_LoginWithIncorrectPassword() {
        loginPage.inputToEmailTextbox("automationeditor");
        loginPage.clickToLoginContinueButton();
        loginPage.waitForPassWordTextboxtVisible("password");
        loginPage.inputToPasswordTextbox("123");
        loginPage.clickToLoginContinueButton();
        loginPage.waitForErrorMessageVisible();
        Assert.assertEquals(loginPage.getEmailErrorMessage(),
                "Oops, that's not the right password. Please try again!");
    }

    @Test
    public void TC06_LoginValidEmailAndPassword() {
        loginPage.inputToEmailTextbox("automationeditor");
        loginPage.clickToLoginContinueButton();
        loginPage.waitForPassWordTextboxtVisible("password");
        loginPage.inputToPasswordTextbox("automationfc");
        dashBoardPage = loginPage.clickToLoginButton();
        dashBoardPage.waitForDashBoardHeaderVisible();
        Assert.assertEquals(dashBoardPage.getHeaderText(), "Dashboard");
        dashBoardPage.clickToAccountImage();
        dashBoardPage.clickToLogOutButton();
        dashBoardPage.elementIsDisplay();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
