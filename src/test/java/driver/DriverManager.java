package driver;

import constants.ServiceConstants;
import service.ConfigFileReader;
import service.IConfigFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;



public class DriverManager {

    public DriverManager() {
    }

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private Browsers browser;

    private static final IConfigFileReader driverConfig = new ConfigFileReader(ServiceConstants.PATH_TO_DRIVER_PROPERTY_FILE.getValue());
    private static final Logger logger = LogManager.getRootLogger();


    public WebDriver getDriver() {
        browser = Browsers.valueOf(driverConfig.getBrowser());
        if (driver.get() == null) {
            if (driverConfig.isLocal()) {
                driver.set(browser.getLocalWebDriver());
            }
        }
        return driver.get();
    }

    public void closeDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }

    public Browsers getBrowser() {
        return browser;
    }

}
