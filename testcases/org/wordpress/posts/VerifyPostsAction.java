package org.wordpress.posts;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

class VerifyPostsAction extends AbstractPage {
    WebDriver driver;

    String emailLogin = "automationeditor";
    String passwordLogin = "automationfc";

    String emailTextbox = "//input[@id='usernameOrEmail']";
    String passwordTextbox = "//input[@id='password']";
    String continueButton = "//button[text()='Continue']";
    String loginButton = "//button[text()='Log In']";
    String dashboardText = "//h1[text()='Dashboard']";

    String postsTextArea = "//div[text()='Posts']";
    String allPostsTextArea = "//a[text()='All Posts']";
    String allTexArea = "//a[@class='current']";

    @BeforeClass
    public void beforeClass() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        openAnyUrl(driver, "https://automationfc.wordpress.com/wp-admin/index.php");
    }

    @Test
    public void actionWithAllPosts() {
        sendTextToElement(driver, emailTextbox, emailLogin);
        clickToElement(driver, continueButton);
        sendTextToElement(driver, passwordTextbox, passwordLogin);
        clickToElement(driver, loginButton);
//        isElementDisplayed(driver, dashboardText);

        hoverMouseToElement(driver, postsTextArea);
        clickToElement(driver, allPostsTextArea);
//        isElementDisplayed(driver, allTexArea);
    }

    public void actionWithAddNew() {

    }

    public void actionWithCopy() {

    }

    public void actionWithTag() {

    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

}
