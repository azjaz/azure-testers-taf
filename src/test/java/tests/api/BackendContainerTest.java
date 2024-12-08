package tests.api;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import models.ColumnPizzaModelRQ;
import models.Pizza;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BackendContainerTest extends BaseBackendTest{

    private Response response = null;
    private String testPizza = "Xavier";

    @BeforeEach
    @Override
    protected void setup() {
        super.setup();
        ColumnPizzaModelRQ body = ColumnPizzaModelRQ.builder()
                .pizzas(new Pizza[]
                        {Pizza.builder().name("Xavier").ingredients(new String[]{"Onion", "Jam", "Cheese"}).build(),
                        Pizza.builder().name("Yvonne").ingredients(new String[]{"Ketchup", "Cheese"}).build()})
                .build();
        response = getRestClient().getResponse(config.getApiHost(), Method.POST, body);
    }

    @Test
    void getStatusCode() {
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getPizzaIngredientsBody() {
        String responseRepresentation = response.asString();
        Assertions.assertThat(responseRepresentation).isNotEmpty();
    }
}
