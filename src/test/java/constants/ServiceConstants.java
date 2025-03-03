package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServiceConstants {
    PATH_TO_DRIVER_PROPERTY_FILE("src/test/resources/driver.properties"),
    PATH_TO_AZURE_PROPERTIES_FILE("src/test/resources/azure.properties"),
    AZURE_AUTH_URL("https://login.microsoftonline.com/"),
    AZURE_OAUTH_PATH("/oauth2/token"),
    AZURE_MGMT_URL("https://management.azure.com/"),
    AZURE_SUBSCRIPTIONS_NODE("subscriptions/"),
    AZURE_RG_NODE("/resourceGroups/"),
    AZURE_PUBLIC_IP_RESOURCE("/providers/Microsoft.Network/publicIPAddresses/"),
    AZURE_CONTAINER_APP_RESOURCE("/providers/Microsoft.App/containerApps/"),
    AZURE_FUNCTION_APP_RESOURCE("/providers/Microsoft.Web/sites/"),
    AZURE_FUNCTIONS_NODE("/functions/"),
    API("api/"),
    PORT(":3000/"),
    FUNCTION("GetPrice"),
    PATH_TO_DOWNLOAD_FOLDER("C://Downloads"),
    PATH_TO_IMAGE_FILE("src/test/resources/img/testPizza"),
    PATH_TO_INGREDIENTS("our_ingredients");


    private final String value;


    }
