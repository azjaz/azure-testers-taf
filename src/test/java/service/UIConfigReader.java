package service;

public class UIConfigReader extends AbstractConfigReader {

    public UIConfigReader(String propertiesPath) {
        super(propertiesPath);
    }


    public String getBrowser() {
        String browserName = getValue("webdriver.browser");;
        return browserName.toUpperCase();
    }


    public Boolean isLocal() {
        return Boolean.parseBoolean(getValue("webdriver.is-local"));
    }

}
