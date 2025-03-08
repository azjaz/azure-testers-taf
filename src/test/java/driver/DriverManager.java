package driver;

import constants.ServiceConstants;
import service.UIConfigReader;
import service.IConfigFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;



public class DriverManager {

    public DriverManager() {
    }

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private Browsers browser;
    private final UIConfigReader config = new UIConfigReader(ServiceConstants.PATH_TO_DRIVER_PROPERTY_FILE.getValue());
    private static final Logger logger = LogManager.getRootLogger();


    public WebDriver getDriver() {
        browser = Browsers.valueOf(config.getBrowser());
        if (driver.get() == null) {
            if (config.isLocal()) {
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
