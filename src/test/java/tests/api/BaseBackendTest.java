package tests.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ConfigFileReader;
import service.IConfigFileReader;
import utils.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;


public class BaseBackendTest {
    protected final ThreadLocal<RestClient> restClientThreadLocal = new ThreadLocal<>();
    protected final IConfigFileReader config = new ConfigFileReader();

    protected final Logger logger = LogManager.getRootLogger();
    protected final List<String> expectedRegions = List.of("Aurelia", "Brovania", "Caledonia", "Deltaria", "Eldoria");

    protected RestClient getRestClient() {
        return restClientThreadLocal.get();
    }

    @BeforeEach
    protected void setup() {
        RestClient restClient = new RestClient();
        restClientThreadLocal.set(restClient);
        getRestClient().setBaseURI(config.getApiHost());

    }

    @AfterEach
    protected void afterClass() {
        restClientThreadLocal.remove();
    }

}
