package utils;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static io.restassured.RestAssured.given;

public class RestClient {
    private final Logger logger = LogManager.getRootLogger();
    public void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    public <T> Response getResponse(String basePath, Method method, T body) {
        RequestSpecification spec = given()
                .contentType(ContentType.JSON)
                .basePath(basePath);

        if (body != null) {
            spec = spec.body(body);
        }
        switch (method) {
            case POST:
                return spec
                        .when()
                        .post()
                        .andReturn();
            case DELETE:
                return spec
                        .when()
                        .delete()
                        .andReturn();
            default:
                return spec
                        .when()
                        .request(method)
                        .andReturn();
        }
    }

    public Response getResponse(String basePath, Method method) {
        return getResponse(basePath, method, null);
    }

    public List<String> getExpectedValuesFromJsonFile(String fileName) {
        List<String> valuesFromJsonFile = null;
        try(FileReader reader = new FileReader(Paths.get(fileName).toFile())) {
            valuesFromJsonFile = new Gson().fromJson(reader, List.class);
        } catch (IOException e) {
            logger.error("Json file was not read");
        }
        return valuesFromJsonFile;
    }


}