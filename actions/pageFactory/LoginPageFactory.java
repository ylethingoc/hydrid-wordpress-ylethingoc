package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LoginPageFactory {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//input[@id='usernameOrEmail']")
    private WebElement userNameTextBox;

    @FindBy(how = How.XPATH, using = "//input[@id='password']")
    private WebElement passWordTextBox;

    @FindBy(how = How.XPATH, using = "//button[@type='submit']")
    private WebElement loginContinueButton;

    @FindBy(how = How.XPATH, using = "//div[@role='alert']/span")
    private WebElement errorMessage;

    public LoginPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void inputToEmailTextbox(String email) {
        userNameTextBox.sendKeys(email);
    }

    public void inputToPasswordTextbox(String password) {
        passWordTextBox.sendKeys(password);
    }

    public String getEmailErrorMessage() {
        return errorMessage.getText();
    }

    public void clickToLoginContinueButton() {
        loginContinueButton.click();
    }
}
