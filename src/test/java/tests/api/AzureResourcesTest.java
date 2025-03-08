package tests.api;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class AzureResourcesTest extends BaseBackendTest {

    private final List<String> listOfInstancesTypes = Arrays.asList("networkSecurityGroups", "publicIPAddresses", "virtualNetworks",
            "networkInterfaces", "virtualMachines", "disks", "extensions");

    @Test
    void checkVMStatus() {
        String vmName = "vm-" + config.getVMResourceName().get();
        String vmStatusUrl = azureCommonURL + "/providers/Microsoft.Compute/virtualMachines/" + vmName + "?api-version=2021-03-01";

        Response response = config.getAzureResourceResponse(vmStatusUrl);
        String vmStatus = response.jsonPath().getString("properties.provisioningState");

        Assertions.assertThat(vmStatus).hasToString("Succeeded");

    }

    @Test
    void checkAllResources() {

        String allResourcesUrl = azureCommonURL + "/resources?api-version=2021-01-01";
        Response response = config.getAzureResourceResponse(allResourcesUrl);

        List<String> resourceTypes = response.jsonPath().getList("value.type");
                List<String> listOfTypesFromRequest = resourceTypes.stream()
                        .map(s -> Arrays.asList(s.split("/")).subList(1, s.split("/").length))
                        .flatMap(List::stream)
                        .distinct()
                        .collect(Collectors.toList());

        Assertions.assertThat(listOfTypesFromRequest).containsAll(listOfInstancesTypes);
    }
}


