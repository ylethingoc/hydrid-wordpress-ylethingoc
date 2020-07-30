package commons;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;

public abstract class AbstractPage {
    private Select select;
    private Actions action;
    private WebElement element;
    private JavascriptExecutor jsExecutor;
    private WebDriverWait explicitWait;
    private List<WebElement> elements;
    private static final long longTimeout = 30;

    public String castRestParameter(String locator, String... values) {
        locator = String.format(locator,(Object[]) values);
        return locator;
    }
    public void openAnyUrl(WebDriver driver, String url) {
        driver.get(url);
    }

    public String getPageTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public String getCurrentUrl(WebDriver driver) {
        return driver.getCurrentUrl();
    }

    public void back(WebDriver driver) {
        driver.navigate().back();
    }

    public void forward(WebDriver driver) {
        driver.navigate().forward();
    }

    public void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void acceptAlert(WebDriver driver) {
        driver.switchTo().alert().accept();
    }

    public void cancelAlert(WebDriver driver) {
        driver.switchTo().alert().dismiss();
    }

    public void sendKeyToAlert(WebDriver driver, String value) {
        driver.switchTo().alert().sendKeys(value);
    }

    public String getTextAlert(WebDriver driver) {
        return driver.switchTo().alert().getText();
    }

    public void waitAlertPresence(WebDriver driver) {
        explicitWait = new WebDriverWait(driver, 15);
        explicitWait.until(ExpectedConditions.alertIsPresent());
    }

    public void switchToWindowByID(WebDriver driver, String parentID) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String runWindow : allWindows) {
            if (!runWindow.equals(parentID)) {
                driver.switchTo().window(runWindow);
                break;
            }
        }
    }

    public void switchToWindowByTittle(WebDriver driver, String tittle) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String id : allWindows) {
            driver.switchTo().window(id);
            String windowTitle = driver.getTitle();
            if (windowTitle.equals(tittle)) {
                break;
            }
        }
    }

    public boolean areAllWindowsWithoutParent(WebDriver driver, String parentWindow) {
        Set<String> allWindows = driver.getWindowHandles();
        for (String id : allWindows) {
            if (!id.equals(parentWindow)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
        driver.switchTo().window(parentWindow);
        return driver.getWindowHandles().size() == 1;
    }

    public WebElement findElementByXpath(WebDriver driver, String locator) {
        return driver.findElement(byXpath(locator));
    }

    public List<WebElement> findElementsXpath(WebDriver driver, String locator) {
        return driver.findElements(byXpath(locator));
    }

    public By byXpath(String locator) {
        return By.xpath(locator);
    }

    public void clearTextInElement(WebDriver driver, String locator) {
        findElementByXpath(driver, locator).clear();
    }

    public void clearTextInElement(WebDriver driver, String locator, String... dynamicValue) {
        findElementByXpath(driver, castRestParameter(locator,dynamicValue)).clear();
    }

    public void sendTextToElement(WebDriver driver, String locator, String key) {
        driver.findElement(By.xpath(locator)).sendKeys(key);
    }

    public void sendTextToElement(WebDriver driver, String locator, String key, String... dynamicValue) {
        driver.findElement(By.xpath(castRestParameter(locator,dynamicValue))).sendKeys(key);
    }

    public void clickToElement(WebDriver driver, String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    public void clickToElement(WebDriver driver, String locator, String... dynamicValue) {
        driver.findElement(By.xpath(castRestParameter(locator,dynamicValue))).click();
    }

    public String getElementText(WebDriver driver, String locator) {
        return findElementByXpath(driver, locator).getText().trim();
    }

    public String getElementText(WebDriver driver, String locator, String... dynamicValue) {
        return findElementByXpath(driver, castRestParameter(locator,dynamicValue)).getText().trim();
    }


    public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
        return findElementByXpath(driver, locator).getAttribute(attributeName);
    }

    public void selectValueInDropDown(WebDriver driver, String locator, String value) {
        select = new Select(findElementByXpath(driver, locator));
        select.selectByVisibleText(value);
    }

    public String getSelectedItemInDropdown(WebDriver driver, String locator) {
        select = new Select(findElementByXpath(driver, locator));
        return select.getFirstSelectedOption().getText();
    }

    public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String allItemXpath,
                                           String expectedValueItem) throws InterruptedException {
        element = findElementByXpath(driver, parentXpath);

        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", element);
        sleepInSecond(1);

        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byXpath(allItemXpath)));

        elements = findElementsXpath(driver, allItemXpath);

        for (WebElement childElement : elements) {
            if (childElement.getText().equals(expectedValueItem)) {
                if (childElement.isDisplayed()) {
                    childElement.click();
                } else {
                    jsExecutor.executeScript("arguments[0].scrollIntoView(true);", childElement);
                    sleepInSecond(1);
                    jsExecutor.executeScript("arguments[0].click();", childElement);
                }
                sleepInSecond(1);
                break;
            }
        }
    }

    public int countElementNumber(WebDriver driver, String locator) {
        elements = findElementsXpath(driver, locator);
        return elements.size();
    }

    public void checkToCheckBox(WebDriver driver, String locator) {
        element = findElementByXpath(driver, locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void unCheckToCheckBox(WebDriver driver, String locator) {
        element = findElementByXpath(driver, locator);
        if (element.isSelected()) {
            element.click();
        }
    }

    public boolean isElementDisplay(WebDriver driver, String locator) {
        return findElementByXpath(driver, locator).isDisplayed();
    }

    public boolean isElementDisplay(WebDriver driver, String locator, String... dynamicValue) {
        return findElementByXpath(driver, castRestParameter(locator, dynamicValue)).isDisplayed();
    }

    public boolean isElementSelected(WebDriver driver, String locator) {
        return findElementByXpath(driver, locator).isSelected();
    }

    public boolean isElementEnable(WebDriver driver, String locator) {
        return findElementByXpath(driver, locator).isEnabled();
    }

    public void switchToFrameOrIFrame(WebDriver driver, String locator) {
        driver.switchTo().frame(findElementByXpath(driver, locator));
    }

    public void defaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public void hoverMouseToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.moveToElement(findElementByXpath(driver, locator)).perform();
    }

    public void doubleClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.doubleClick(findElementByXpath(driver, locator)).perform();
    }

    public void rightClickToElement(WebDriver driver, String locator) {
        action = new Actions(driver);
        action.contextClick(findElementByXpath(driver, locator)).perform();
    }

    public void sendKeyToElement(WebDriver driver, String locator, Keys key) {
        action = new Actions(driver);
        action.sendKeys(findElementByXpath(driver, locator), key).perform();
    }

    public Object executeForBrowser(WebDriver driver, String javaScript) {
        jsExecutor = (JavascriptExecutor) driver;
        return jsExecutor.executeScript(javaScript);
    }

    public boolean verifyTextInInnerText(WebDriver driver, String expectedText) {
        jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('"
                + expectedText + "')[0]");
        return textActual.equals(expectedText);
    }

    public void scrollToBottomPage(WebDriver driver) {
        jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
    }

    public void highlightElement(WebDriver driver, String locator) throws InterruptedException {
        jsExecutor = (JavascriptExecutor) driver;
        element = findElementByXpath(driver, locator);
        String originalStyle = element.getAttribute("style");
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                "border: 5px solid red; border-style: dashed;");
        sleepInSecond(1);
        jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
                originalStyle);

    }

    public void clickToElementByJS(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        element = findElementByXpath(driver, locator);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    public void scrollToElement(WebDriver driver, String locator) {
        jsExecutor = (JavascriptExecutor) driver;
        element = findElementByXpath(driver, locator);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
        jsExecutor = (JavascriptExecutor) driver;
        element = findElementByXpath(driver, locator);
        jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
    }

    public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
        jsExecutor = (JavascriptExecutor) driver;
        element = findElementByXpath(driver, locator);
        jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
    }

    public boolean isImageLoaded(WebDriver driver, String locator) {
        element = findElementByXpath(driver, locator);
        jsExecutor = (JavascriptExecutor) driver;
        return (boolean) jsExecutor.
                executeScript("return arguments[0].complete && typeof arguments[0]"
                        + ".naturalWidth != \"undefined\" && arguments[0]"
                        + ".naturalWidth >0 ", findElementByXpath(driver, locator));
    }

    public boolean verifyTextInnerText(WebDriver driver, String expectedText) {
        jsExecutor = (JavascriptExecutor) driver;
        String textActual = (String) jsExecutor.
                executeScript("return document.documentElement.innerText.match(" + expectedText + ")[0]");
        return textActual.equals(expectedText);
    }

    public void waitForElementVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath(locator)));
    }

    public void waitForElementVisible(WebDriver driver, String locator, String... dynamicValue) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(byXpath(castRestParameter(locator, dynamicValue))));
    }

    public void waitForElementInVisible(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(byXpath(locator)));

    }

    public void waitForElementClickable(WebDriver driver, String locator) {
        explicitWait = new WebDriverWait(driver, longTimeout);
        explicitWait.until(ExpectedConditions.elementToBeClickable(byXpath(locator)));

    }

    public void sleepInSecond(long timeout) throws InterruptedException {
        Thread.sleep(timeout * 1000);
    }
}
