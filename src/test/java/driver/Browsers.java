package driver;

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
            prefs.put("profile.default_content_settings.popups", 0); // Disable popup blocking
            prefs.put("download.default_directory", "C:\\Downloads"); // Set default download directory
            prefs.put("download.prompt_for_download", false); // Disable download prompt
            prefs.put("download.directory_upgrade", true); // Automatically upgrade to the specified directory
            prefs.put("safebrowsing.enabled", true); // Enable safe browsing

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

    private static final IConfigFileReader driverConfig = new ConfigFileReader();

    public abstract WebDriver getLocalWebDriver();


}

