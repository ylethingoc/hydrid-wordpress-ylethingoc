package pageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class DashBoardPageFactory {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//h1[text()='Dashboard']")
    WebElement headerText;

    public DashBoardPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getHeaderText() {
        return headerText.getText();
    }
}
