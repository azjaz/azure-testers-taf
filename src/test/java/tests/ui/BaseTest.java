package tests.ui;


import constants.ServiceConstants;
import driver.DriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import pages.StartPage;
import service.AzureConfigReader;

import java.util.List;
import java.util.Random;


public abstract class BaseTest {

    private WebDriver driver;
    private String host;
    private DriverManager manager;
    private final AzureConfigReader config = new AzureConfigReader(ServiceConstants.PATH_TO_AZURE_PROPERTIES_FILE.getValue());

    protected final List<String> expectedPizzaItemsFirstPage = List.of("Quattro Formaggi", "Capricciosa",
            "Hawaiian", "Rustica", "Margherita", "Pugliese");
    protected final List<String> expectedPizzaItemsSecondPage = List.of("Quattro Stagioni", "Sicilian",
            "Marinara");
    protected final List<String> expectedIngredientsItems = List.of("Pepperoni", "Mushrooms",
            "Onions", "Bell Peppers", "Olives", "Pineapple", "Ham", "Sausage", "Bacon", "Jalapenos", "Tomatoes");

    protected final int testNumber = new Random().ints(3, 9).findFirst().getAsInt();
    protected final String testString = RandomStringUtils.randomAlphabetic(testNumber);

    BaseTest() {

    }


    @BeforeEach
    protected void browserSetUp() {
        manager = new DriverManager();
        driver = manager.getDriver();
        host = "http://" + config.getAzureHost();
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
