package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigFileReader extends AbstractConfigReader {
//    private static final Properties properties = new Properties();

    public ConfigFileReader(String propertiesPath) {
        super(propertiesPath);
    }


    public String getBrowser() {
        String browserName = properties.getProperty("webdriver.browser");
        return browserName.toUpperCase();
    }


    public Boolean isLocal() {
        return Boolean.parseBoolean(properties.getProperty("webdriver.is-local"));
    }


    public String getApplication() {
        return properties.getProperty("app.name");
    }


    public String getApiHost() {
        return properties.getProperty("api.host");
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }

}
