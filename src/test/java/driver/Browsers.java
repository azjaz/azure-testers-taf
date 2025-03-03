package driver;

import constants.ServiceConstants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import service.ConfigFileReader;
import service.IConfigFileReader;

import java.util.HashMap;
import java.util.Map;


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
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("download.default_directory", "C:\\Downloads");
            prefs.put("download.prompt_for_download", false);
            prefs.put("download.directory_upgrade", true);
            prefs.put("safebrowsing.enabled", true);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            options.setAcceptInsecureCerts(true);
            if (Boolean.parseBoolean(System.getProperty("headless"))) {
                options.addArguments("--headless=new");
            }
            options.addArguments("ignore-certificate-errors");
            options.addArguments("allow-running-insecure-content");
            options.addArguments("unsafely-treat-insecure-origin-as-secure=http://52.142.42.99");
            options.setExperimentalOption("prefs", prefs);

            WebDriverManager.chromedriver().setup();
            return new ChromeDriver(options);
        }

    };

    private static final IConfigFileReader driverConfig = new ConfigFileReader(ServiceConstants.PATH_TO_DRIVER_PROPERTY_FILE.getValue());

    public abstract WebDriver getLocalWebDriver();


}

