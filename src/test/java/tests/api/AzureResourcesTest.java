package tests.api;

import constants.ServiceConstants;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import service.ConfigFileReader;
import service.IConfigFileReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static service.AzureConnectConfig.getAzureResourceResponse;

class AzureResourcesTest {

    private static final IConfigFileReader azureConfig = new ConfigFileReader(ServiceConstants.PATH_TO_AZURE_PROPERTIES_FILE.getValue());
    private static final String SUBSCRIPTION_ID = azureConfig.getValue("azure.subscription");
    private static final String RESOURCE_GROUP = azureConfig.getValue("azure.rg");
    private static final String VM_NAME = "vm-oculus";

    private final List<String> listOfInstancesTypes = Arrays.asList("networkSecurityGroups", "publicIPAddresses", "virtualNetworks",
            "networkInterfaces", "virtualMachines", "disks", "extensions");

    @Test
    void checkVMStatus() {
        String vmStatusUrl = "https://management.azure.com/subscriptions/" + SUBSCRIPTION_ID +
                "/resourceGroups/" + RESOURCE_GROUP + "/providers/Microsoft.Compute/virtualMachines/" + VM_NAME + "?api-version=2021-03-01";

        Response response = getAzureResourceResponse(vmStatusUrl);
        String vmStatus = response.jsonPath().getString("properties.provisioningState");

        Assertions.assertThat(vmStatus).hasToString("Succeeded");

    }

    @Test
    void checkAllResources() {
        String allResourcesUrl = "https://management.azure.com/subscriptions/" + SUBSCRIPTION_ID +
                "/resourceGroups/" + RESOURCE_GROUP + "/resources?api-version=2021-01-01";

        Response response = getAzureResourceResponse(allResourcesUrl);

        System.out.println(response.getBody().asString());

        List<String> resourceTypes = response.jsonPath().getList("value.type");
                List<String> listOfTypesFromRequest = resourceTypes.stream()
                        .map(s -> Arrays.asList(s.split("/")).subList(1, s.split("/").length))
                        .flatMap(List::stream)
                        .distinct()
                        .collect(Collectors.toList());


        Assertions.assertThat(listOfTypesFromRequest).containsAll(listOfInstancesTypes);
    }
}


