package tests.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ConfigFileReader;
import service.IConfigFileReader;
import utils.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class BaseBackendTest {
    private final ThreadLocal<RestClient> restClientThreadLocal = new ThreadLocal<>();
    protected final IConfigFileReader config = new ConfigFileReader();

    protected final Logger logger = LogManager.getRootLogger();

    protected RestClient getRestClient() {
        return restClientThreadLocal.get();
    }

    @BeforeEach
    protected void setup() {
        RestClient restClient = new RestClient();
        restClientThreadLocal.set(restClient);
        String appName = config.getApplication();
        if (appName.equals("atrium") || appName.equals("balcony")) {
            getRestClient().setBaseURI("http://" + config.getHost());
        } else {
            getRestClient().setBaseURI(config.getApiHost());
        }
    }

    @AfterEach
    protected void afterClass() {
        restClientThreadLocal.remove();
    }

}
