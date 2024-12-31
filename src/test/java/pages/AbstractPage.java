package pages;


import driver.DriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public abstract class AbstractPage {

    protected WebDriver driver;
    protected static final long DRIVER_TIMEOUT = 10;

    @FindBy(xpath = "//button[contains(text(), 'Submit')]")
    protected WebElement submitButton;

    public AbstractPage() {
        DriverManager manager = new DriverManager();
        this.driver = manager.getDriver();
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitVisibility(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(DRIVER_TIMEOUT)).pollingEvery(Duration.ofSeconds(1))
                .until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitInvisibility(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DRIVER_TIMEOUT)).pollingEvery(Duration.ofSeconds(1))
                .until(ExpectedConditions.invisibilityOf(element));
    }

    public void clickElementWithJS(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}


