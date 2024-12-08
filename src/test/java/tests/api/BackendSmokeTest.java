package tests.api;

import constants.ServiceConstants;
import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BackendSmokeTest extends BaseBackendTest{

    private Response response = null;

    @BeforeEach
    @Override
    protected void setup() {
        super.setup();
        response = getRestClient().getResponse(ServiceConstants.PATH_TO_INGREDIENTS.getValue(), Method.GET);
    }

    @Test
    void getStatusCode() {
        response.then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }
}
