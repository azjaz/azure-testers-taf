package tests.api;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DomeContainerTest extends BaseBackendTest{
    private Response response = null;

    @BeforeEach
    @Override
    protected void setup() {
        super.setup();
        String path = "/";
        String incorrectPizzaBody = "{ \"Pizzas\": [ { \"Name\": \"Xavier1\", \"Name1\": \"Xavier\", \"Ingredients\": [ \"Onion1\", \"Jam\", \"Cheese\" ] } ] }";
        getRestClient().setBaseURI(config.getContainerAppHost());
        response = getRestClient().getResponse(path, Method.POST, incorrectPizzaBody);
    }

    @Test
    void getStatusCode() {
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
