package tests.api;

import constants.ServiceConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.AzureConfigReader;
import utils.RestClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static constants.ServiceConstants.*;


public class BaseBackendTest {
    protected final ThreadLocal<RestClient> restClientThreadLocal = new ThreadLocal<>();
    protected AzureConfigReader config = new AzureConfigReader(ServiceConstants.PATH_TO_AZURE_PROPERTIES_FILE.getValue());

    protected final Logger logger = LogManager.getRootLogger();
    protected final List<String> expectedRegions = List.of("Aurelia", "Brovania", "Caledonia", "Deltaria", "Eldoria");
    protected final String azureCommonURL = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
            + config.getValue("azure.subscription") + AZURE_RG_NODE.getValue()
            + config.getValue("azure.rg");

    protected RestClient getRestClient() {
        return restClientThreadLocal.get();
    }

    @BeforeEach
    protected void setup() {
        RestClient restClient = new RestClient();
        restClientThreadLocal.set(restClient);
    }

    @AfterEach
    protected void afterClass() {
        restClientThreadLocal.remove();
    }

}
