package tests.api;

import io.restassured.http.Method;
import io.restassured.response.Response;
import models.function.egress.Ingredient;
import models.function.facade.FacadeRegionModelRS;
import models.function.hallway.HallwayBulkModelRQ;
import models.function.hallway.HallwayBulkModelRS;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static constants.ServiceConstants.API;
import static constants.ServiceConstants.FUNCTION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FunctionAppHallwayTest extends BaseBackendTest {

    private Response response = null;
    private final Map<String, String> params = new HashMap<>();

    private HallwayBulkModelRS hallwayBulkModelRS = null;
    @BeforeEach
    @Override
    protected void setup() {
        super.setup();
        HallwayBulkModelRQ body = HallwayBulkModelRQ.builder()
                .region(expectedRegions.get(1))
                .bulkIngredients(new Ingredient[][]{new Ingredient[]
                                {Ingredient.builder().name("Onions").quantity(2).build(),
                                        Ingredient.builder().name("Bell Peppers").quantity(1).build()},
                        new Ingredient[]
                                {Ingredient.builder().name("Onions").quantity(2).build()}})
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
        double expectedTotalPrice = 9.984;
        hallwayBulkModelRS = response.as(HallwayBulkModelRS.class);
        double totalPriceFromRS = hallwayBulkModelRS.getTotalPrice();
        assertThat(totalPriceFromRS, equalTo(expectedTotalPrice));
    }

    @Test
    void getPrice() {
        double expectedPrice = 8.32;
        hallwayBulkModelRS = response.as(HallwayBulkModelRS.class);
        double priceFromRS = hallwayBulkModelRS.getPrice();
        assertThat(priceFromRS, equalTo(expectedPrice));
    }

    @Test
    void getTotalPriceFromIngredients() {
        hallwayBulkModelRS = response.as(HallwayBulkModelRS.class);
        double totalPriceFromRS = hallwayBulkModelRS.getTotalPrice();
        double ingredientPriceFromRS = Arrays.stream(hallwayBulkModelRS.getBulkIngredients())
                .mapToDouble(FacadeRegionModelRS::getTotalPrice)
                .sum();
        assertThat(ingredientPriceFromRS, equalTo(totalPriceFromRS));
    }
}
