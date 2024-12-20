package tests.ui;


import pages.StartPage;
import driver.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import java.util.List;


public abstract class BaseTest {

    private WebDriver driver;
    private String host;
    private DriverManager manager;

    protected final List<String> expectedPizzaItemsFirstPage = List.of("Quattro Formaggi", "Capricciosa",
            "Hawaiian", "Rustica", "Margherita", "Pugliese");
    protected final List<String> expectedPizzaItemsSecondPage = List.of("Quattro Stagioni", "Sicilian",
            "Marinara");


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
