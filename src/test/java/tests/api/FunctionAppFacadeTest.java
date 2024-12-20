package tests.api;

import io.restassured.http.Method;
import io.restassured.response.Response;
import models.function.egress.Ingredient;
import models.function.facade.FacadeRegionModelRQ;
import models.function.facade.FacadeRegionModelRS;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static constants.ServiceConstants.API;
import static constants.ServiceConstants.FUNCTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FunctionAppFacadeTest extends BaseBackendTest {
    private Response response = null;
    private Map<String, String> params = new HashMap<>();
    private FacadeRegionModelRS facadeRegionModelRS = null;

    @BeforeEach
    @Override
    protected void setup() {
        super.setup();
        FacadeRegionModelRQ body = FacadeRegionModelRQ.builder()
                .region("Aurelia")
                .ingredients(new Ingredient[]
                        {Ingredient.builder().name("Onions").quantity(2).build(),
                                Ingredient.builder().name("Bell Peppers").quantity(1).build()})
                .build();
        String path = API.getValue() + FUNCTION.getValue();
        params.put("code", config.getValue("api.function.token"));
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
        double expectedTotalPrice = 6.5373;
        facadeRegionModelRS = response.as(FacadeRegionModelRS.class);
        double totalPriceFromRS = facadeRegionModelRS.getTotalPrice();
        assertThat(totalPriceFromRS, equalTo(expectedTotalPrice));
    }

    @Test
    void getPrice() {
        double expectedPrice = 5.66;
        facadeRegionModelRS = response.as(FacadeRegionModelRS.class);
        double priceFromRS = facadeRegionModelRS.getPrice();
        assertThat(priceFromRS, equalTo(expectedPrice));
    }
}
