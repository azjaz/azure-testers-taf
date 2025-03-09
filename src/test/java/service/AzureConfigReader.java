package service;

import io.restassured.response.Response;

import java.util.*;
import java.util.stream.Collectors;

import static constants.ServiceConstants.*;
import static io.restassured.RestAssured.given;

public class AzureConfigReader extends AbstractConfigReader {

    private final String AUTH_URL = AZURE_AUTH_URL.getValue() + getValue("azure.tenant")
            + AZURE_OAUTH_PATH.getValue();

    public AzureConfigReader(String propertiesPath) {
        super(propertiesPath);
    }


    private String getAccessToken() {
        logger.info("Obtaining access token");
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "client_credentials");
        params.put("client_id", getValue("azure.client"));
        params.put("client_secret", getValue("azure.secret"));
        params.put("resource", AZURE_MGMT_URL.getValue());
        logger.info("params map " + params.get("grant_type"));
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(params)
                .post(AUTH_URL);

        if (response.getStatusCode() == 200) {
            logger.info("Access token is set");
            return response.jsonPath().getString("access_token");
        } else {
            logger.warn("Failed to obtain access token: " + response.getBody().asString());
            throw new RuntimeException("Failed to obtain access token: " + response.getBody().asString());
        }
    }

    public String getAzureHost() {
        String apiURL = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + getValue("azure.subscription") + AZURE_RG_NODE.getValue()
                + getValue("azure.rg") + AZURE_PUBLIC_IP_RESOURCE.getValue()
                + getPublicIPResourceName().get() + "?api-version=2024-05-01";

        Response response = getAzureResourceResponse(apiURL);

        String apiHost = response.jsonPath().getString("properties.ipAddress");

        return apiHost + PORT.getValue();

    }

    public String getContainerAppHost() {
        String containerURL = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + getValue("azure.subscription") + AZURE_RG_NODE.getValue()
                + getValue("azure.rg") + AZURE_CONTAINER_APP_RESOURCE.getValue()
                + getContainerAppResourceName().get() + "?api-version=2024-03-01";

        Response response = getAzureResourceResponse(containerURL);
        return "https://" + response.jsonPath().getString("properties.configuration.ingress.fqdn");

    }

    public String getFunctionAppHost() {
        String functionURL = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + getValue("azure.subscription") + AZURE_RG_NODE.getValue()
                + getValue("azure.rg") + AZURE_FUNCTION_APP_RESOURCE.getValue()
                + getFunctionAppResourceName().get() + "?api-version=2024-04-01";
        Response response = getAzureResourceResponse(functionURL);

        return "https://" + response.jsonPath().getString("properties.defaultHostName")
                + API.getValue();

    }

    private Optional<String> getPublicIPResourceName() {
        List<String> allResources = getAllResourcesNames().jsonPath().getList("value.name");
        return allResources.stream().filter(val -> val.contains("-ip")).findFirst();
    }

    private Optional<String> getContainerAppResourceName() {
        List<String> allResources = getAllResourcesNames().jsonPath().getList("value.name");
        return allResources.stream()
                .filter(val -> val.contains("cont"))
                .filter(val -> !val.contains("env"))
                .findFirst();

    }

    public Optional<String> getVMResourceName() {
        List<String> allResources = getAllResourcesNames().jsonPath().getList("value.name");
        List<String> vmResources = allResources.stream()
                .filter(val -> val.contains("vm-"))
                .collect(Collectors.toList());

        return vmResources.stream()
                .map(val -> Arrays.asList(val.split("-")).subList(1, 2))
                .flatMap(List::stream)
                .distinct()
                .findFirst();
    }

    private Optional<String> getFunctionAppResourceName() {
        List<String> allResources = getAllResourcesNames().jsonPath().getList("value.name");
        return allResources.stream()
                .filter(val -> val.contains("fnapp-"))
                .findFirst();

    }

    public Response getAzureResourceResponse(String apiURL) {
        String accessToken = getAccessToken();
        return given()
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .contentType("application/json")
                .get(apiURL);
    }

    private Response getAllResourcesNames() {
        String allResourcesUrl = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue()
                + getValue("azure.subscription")
                + AZURE_RG_NODE.getValue()
                + getValue("azure.rg")
                + "/resources?api-version=2021-01-01";

        return getAzureResourceResponse(allResourcesUrl);
    }

    public String getFunctionApiToken(String functionName) {
        String functionTokenUrl = AZURE_MGMT_URL.getValue() + AZURE_SUBSCRIPTIONS_NODE.getValue() +
                getValue("azure.subscription") +
                AZURE_RG_NODE.getValue() + getValue("azure.rg") +
                AZURE_FUNCTION_APP_RESOURCE.getValue() +
                getFunctionAppResourceName().get() +
                AZURE_FUNCTIONS_NODE.getValue() +
                functionName +
                "/listKeys?api-version=2024-04-01";

        Response response = getAzureResourceResponse(functionTokenUrl);
        return response.jsonPath().getString("_master");

    }

}


