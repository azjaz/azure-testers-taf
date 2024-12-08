package service;

public interface IConfigFileReader {

    String getBrowser();

    Boolean isLocal();

    String getHost();

    String getApplication();

    String getApiHost();

    String getValue(String key);

}
