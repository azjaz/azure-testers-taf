package driver;

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

//use injection for driver config?
    private static final IConfigFileReader driverConfig = new ConfigFileReader();
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

    public String getHostAddress() {
        return driverConfig.getHost();
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
