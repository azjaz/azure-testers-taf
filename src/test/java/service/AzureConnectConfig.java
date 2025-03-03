package service;

import constants.ServiceConstants;
import io.restassured.response.Response;
import service.AbstractConfigReader;
import service.ConfigFileReader;
import service.IConfigFileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static constants.ServiceConstants.*;
import static io.restassured.RestAssured.given;

public class AzureConnectConfig extends AbstractConfigReader {

    private static final IConfigFileReader azureConfig = new ConfigFileReader(ServiceConstants.PATH_TO_AZURE_PROPERTIES_FILE.getValue());
    private static final String AUTH_URL = AZURE_AUTH_URL.getValue() + azureConfig.getValue("azure.tenant")
            + AZURE_OAUTH_PATH.getValue();

    public AzureConnectConfig(String propertiesPath) {
        super(propertiesPath);
    }


    private static String getAccessToken() {
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", azureConfig.getValue("azure.client"));
        params.put("client_secret", azureConfig.getValue("azure.secret"));
        params.put("resource", AZURE_MGMT_URL.getValue());

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(params)
                .post(AUTH_URL);

        if (response.getStatusCode() == 200) {
            return response.jsonPath().getString("access_token");
        } else {
            throw new RuntimeException("Failed to obtain access token: " + response.getBody().asString());
        }
    }

    public static String getAzureHost() {
        String apiURL = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + azureConfig.getValue("azure.subscription") + AZURE_RG_NODE.getValue()
                + azureConfig.getValue("azure.rg") + AZURE_PUBLIC_IP_RESOURCE.getValue()
                + getPublicIPResourceName().get() + "?api-version=2024-05-01";

        Response response = getAzureResourceResponse(apiURL);

        String apiHost = response.jsonPath().getString("properties.ipAddress");

        return apiHost + PORT.getValue();

    }

    public static String getContainerAppHost() {
        String containerURL = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + azureConfig.getValue("azure.subscription") + AZURE_RG_NODE.getValue()
                + azureConfig.getValue("azure.rg") + AZURE_CONTAINER_APP_RESOURCE.getValue()
                + getContainerAppResourceName().get() + "?api-version=2024-03-01";

        Response response = getAzureResourceResponse(containerURL);
        return "https://" + response.jsonPath().getString("properties.configuration.ingress.fqdn");

    }

    public static String getFunctionAppHost() {
        String functionURL = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + azureConfig.getValue("azure.subscription") + AZURE_RG_NODE.getValue()
                + azureConfig.getValue("azure.rg") + AZURE_FUNCTION_APP_RESOURCE.getValue()
                + getFunctionAppResourceName().get() + "?api-version=2024-04-01";
        Response response = getAzureResourceResponse(functionURL);

        return "https://" + response.jsonPath().getString("properties.defaultHostName")
                + API.getValue();

    }

    private static Optional<String> getPublicIPResourceName() {
        List<String> allResources = getAllResourcesNames().jsonPath().getList("value.name");
        return allResources.stream().filter(val -> val.contains("-ip")).findFirst();
    }

    private static Optional<String> getContainerAppResourceName() {
        List<String> allResources = getAllResourcesNames().jsonPath().getList("value.name");
        return allResources.stream()
                .filter(val -> val.contains("cont"))
                .filter(val -> !val.contains("env"))
                .findFirst();

    }

    private static Optional<String> getFunctionAppResourceName() {
        List<String> allResources = getAllResourcesNames().jsonPath().getList("value.name");
        return allResources.stream()
                .filter(val -> val.contains("fnapp-"))
                .findFirst();

    }

    public static Response getAzureResourceResponse(String apiURL) {
        String accessToken = getAccessToken();
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .contentType("application/json")
                .get(apiURL);
    }

    public static Response getAllResourcesNames() {
        String allResourcesUrl = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + azureConfig.getValue("azure.subscription")
                + AZURE_RG_NODE.getValue()
                + azureConfig.getValue("azure.rg")
                + "/resources?api-version=2021-01-01";

        return getAzureResourceResponse(allResourcesUrl);
    }

    public static String getFunctionApiToken(String functionName) {
        String functionTokenUrl = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue() +
                azureConfig.getValue("azure.subscription") +
                AZURE_RG_NODE.getValue() + azureConfig.getValue("azure.rg") +
                AZURE_FUNCTION_APP_RESOURCE.getValue() +
                getFunctionAppResourceName().get() +
                AZURE_FUNCTIONS_NODE.getValue() +
                functionName +
                "/listKeys?api-version=2024-04-01";

        Response response = getAzureResourceResponse(functionTokenUrl);
        return response.jsonPath().getString("_master");

    }

}


