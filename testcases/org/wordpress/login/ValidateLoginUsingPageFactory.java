package org.wordpress.login;

import commons.AbstractPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageFactory.DashBoardPageFactory;
import pageFactory.LoginPageFactory;

import java.util.concurrent.TimeUnit;

public class ValidateLoginUsingPageFactory extends AbstractPage {
    private WebDriver driver;
    LoginPageFactory loginPage;
    DashBoardPageFactory dashboardPage;

    @BeforeClass
    public void beforeClass() {
        WebDriverManager.edgedriver().arch64().setup();
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://automationfc.wordpress.com/wp-admin");
        loginPage = new LoginPageFactory(driver);
        dashboardPage = new DashBoardPageFactory(driver);
    }

    @BeforeMethod
    public void beforeMethod() {
        openAnyUrl(driver, "https://automationfc.wordpress.com/wp-admin");
    }

    @Test
    public void loginWithEmptyEmail() {
        loginPage.clickToLoginContinueButton();
        Assert.assertTrue(loginPage.getEmailErrorMessage().contains("Please enter a username or email address."));
    }

    @Test
    public void loginWithInvalidEmail() {
        loginPage.inputToEmailTextbox("123456@12345");
        loginPage.clickToLoginContinueButton();
        Assert.assertTrue(loginPage.getEmailErrorMessage().contains("User does not exist."));
    }

    @Test
    public void loginWithEmptyPassword() throws InterruptedException {
        loginPage.inputToEmailTextbox("automationeditor");
        loginPage.clickToLoginContinueButton();
        Thread.sleep(2000);
        loginPage.clickToLoginContinueButton();
        Assert.assertTrue(loginPage.getEmailErrorMessage().contains("Don't forget to enter your password."));
    }

    @Test
    public void loginWithIncorrectPassword() {
        loginPage.inputToEmailTextbox("automationeditor");
        loginPage.clickToLoginContinueButton();
        loginPage.inputToPasswordTextbox("123");
        loginPage.clickToLoginContinueButton();
        Assert.assertTrue(loginPage.getEmailErrorMessage().contains("Oops, that's not the right password. Please try again!"));
    }

    @Test
    public void loginWithValidAccount() {
        loginPage.inputToEmailTextbox("automationeditor");
        loginPage.clickToLoginContinueButton();
        loginPage.inputToPasswordTextbox("automationfc");
        loginPage.clickToLoginContinueButton();
        Assert.assertEquals(dashboardPage.getHeaderText(), "Dashboard");
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
