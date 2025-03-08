package tests.api;

import io.restassured.http.Method;
import io.restassured.response.Response;
import models.function.egress.EgressIngredientModelRS;
import models.function.egress.EgressIngredientsModelRQ;
import models.function.egress.Ingredient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static constants.ServiceConstants.FUNCTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class FunctionAppEgressTest extends BaseBackendTest {

    private Response response = null;
    private Map<String, String> params = new HashMap<>();
    private EgressIngredientModelRS ingredientRS = null;

    @BeforeEach
    @Override
    protected void setup() {
        super.setup();
        getRestClient().setBaseURI(config.getFunctionAppHost());
        EgressIngredientsModelRQ body = EgressIngredientsModelRQ.builder()
                .ingredients(new Ingredient[]
                        {Ingredient.builder().name("Onions").quantity(2).build(),
                                Ingredient.builder().name("Bell Peppers").quantity(1).build()})
                .build();
        String path = FUNCTION.getValue();
        params.put("code", config.getFunctionApiToken(FUNCTION.getValue()));
        response = getRestClient().getResponse(path, Method.POST, params, body);
    }

    @Test
    void getStatusCode() {
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getTotalPrice() {
        double expectedTotalPrice = 5.66;
        ingredientRS = response.as(EgressIngredientModelRS.class);
        double totalPriceFromRS = ingredientRS.getTotalPrice();
        assertThat(totalPriceFromRS, equalTo(expectedTotalPrice));
    }

}
