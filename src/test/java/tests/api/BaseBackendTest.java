package tests.api;

import service.ConfigFileReader;
import service.IConfigFileReader;
import utils.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class BaseBackendTest {
    private final ThreadLocal<RestClient> restClientThreadLocal = new ThreadLocal<>();
    protected final IConfigFileReader config = new ConfigFileReader();

    protected RestClient getRestClient() {
        return restClientThreadLocal.get();
    }

    @BeforeEach
    protected void setup() {
        RestClient restClient = new RestClient();
        restClientThreadLocal.set(restClient);
        getRestClient().setBaseURI("http://" + config.getHost());
    }

    @AfterEach
    protected void afterClass() {
        restClientThreadLocal.remove();
    }

}
