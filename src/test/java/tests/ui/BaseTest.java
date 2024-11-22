package tests.ui;


import pages.StartPage;
import driver.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;


public abstract class BaseTest {

    private WebDriver driver;
    private String host;
    private DriverManager manager;


    @BeforeEach
    protected void browserSetUp() {
        manager = new DriverManager();
        driver = manager.getDriver();
        host = "http://" + manager.getHostAddress();
    }
    protected StartPage openPage() {
        driver.get(host);
        return new StartPage();
    }

    @AfterEach
    protected void terminateBrowser() {
        manager.closeDriver();
    }
}
