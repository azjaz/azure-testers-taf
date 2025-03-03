package service;

public interface IConfigFileReader {

    String getBrowser();

    Boolean isLocal();

    String getApplication();

    String getApiHost();

    String getValue(String key);

}
