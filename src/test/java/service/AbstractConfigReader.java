package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class AbstractConfigReader {

    protected final Logger logger = LogManager.getRootLogger();
    protected final Properties properties = new Properties();

    public AbstractConfigReader(String propertiesPath) {
        try (InputStream input = Files.newInputStream(Paths.get(propertiesPath))) {
            properties.load(input);
        } catch (IOException e) {
            Logger logger = LogManager.getRootLogger();
            logger.warn("Such property is not found");
        }
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }
}
