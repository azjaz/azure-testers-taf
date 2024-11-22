package service;

import constants.ServiceConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigFileReader implements IConfigFileReader {
    private static final Properties properties = new Properties();

    public ConfigFileReader() {
        try (InputStream input = Files.newInputStream(Paths.get(ServiceConstants.PATH_TO_DRIVER_PROPERTY_FILE.getValue()))) {
            properties.load(input);
        } catch (IOException e) {
            Logger logger = LogManager.getRootLogger();
            logger.warn("Driver.properties is not found at " + ServiceConstants.PATH_TO_DRIVER_PROPERTY_FILE.name());
        }
    }

    @Override
    public String getBrowser() {
        String browserName = properties.getProperty("webdriver.browser");
        return browserName.toUpperCase();
    }

    @Override
    public Boolean isLocal() {
        return Boolean.parseBoolean(properties.getProperty("webdriver.is-local"));
    }

    @Override
    public String getHost() {
        return properties.getProperty("http.host");
    }

    @Override
    public String getValue(String key) {
        return properties.getProperty(key);
    }


}
