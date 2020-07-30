package commons;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class AbstractTest {
    WebDriver driver;

    public WebDriver getBrowserDriver(String browserName) {
        if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("chrome")) {
            //System.setProperty("webdriver.chrome.driver", ".\\broswerDriver\\chromedriver.exe");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
//        } else if (browserName.equalsIgnoreCase("edge")) {
//            //System.setProperty("webdriver.edge.driver", ".\\broswerDriver\\msedgedriver.exe");
//            WebDriverManager.edgedriver().arch64().setup();
//            driver = new EdgeDriver();

        } else {
            System.out.println("Please choose the broswer");
        }
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://automationfc.wordpress.com/wp-admin/");
        return driver;
    }

    public void closeBrowser() {
        driver.quit();
    }
}
