package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import service.ConfigFileReader;
import service.IConfigFileReader;


public enum Browsers {
    FIREFOX {
        @Override
        public WebDriver getLocalWebDriver() {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("start-maximized");
            return new FirefoxDriver(options);
        }

    },
    CHROME {
        @Override
        public WebDriver getLocalWebDriver() {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.setAcceptInsecureCerts(true);
            if (Boolean.parseBoolean(System.getProperty("headless"))) {
                options.addArguments("--headless=new");
            }
            options.addArguments("ignore-certificate-errors");
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(options);
        }

    };

    private static final IConfigFileReader driverConfig = new ConfigFileReader();

    public abstract WebDriver getLocalWebDriver();


}

