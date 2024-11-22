package service;

public interface IConfigFileReader {

    String getBrowser();

    Boolean isLocal();

    String getHost();

    String getValue(String key);

}
