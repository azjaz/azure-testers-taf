package tests.api;

import io.restassured.http.Method;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import models.container.ColumnPizzaModelRQ;
import models.container.Pizza;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static service.AzureConnectConfig.getContainerAppHost;

class BackendContainerTest extends BaseBackendTest{

    private Response response = null;
    private final String reportTitleExpected = "Ingredients distribution";

    @BeforeEach
    @Override
    protected void setup() {
        super.setup();
        String path = "/";
        getRestClient().setBaseURI(getContainerAppHost());
        ColumnPizzaModelRQ body = ColumnPizzaModelRQ.builder()
                .pizzas(new Pizza[]
                        {Pizza.builder().name("Xavier").ingredients(new String[]{"Onion", "Jam", "Cheese"}).build(),
                        Pizza.builder().name("Yvonne").ingredients(new String[]{"Ketchup", "Cheese"}).build()})
                .build();
        response = getRestClient().getResponse(path, Method.POST, body);
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

    @Test
    void getPizzaIngredientsBodyElement() {
        String responseRepresentation = response.asString();
        XmlPath document = new XmlPath(XmlPath.CompatibilityMode.HTML, responseRepresentation);
        String reportTitle = document.getString("html.body.depthFirst()find() { section -> section.h1.text() == 'Ingredients distribution' }");
        Assertions.assertThat(reportTitle).contains(reportTitleExpected);
    }

    @Test
    void getPizzaImageFromBody() {
        String expectedValue = "Image";
        String responseRepresentation = response.asString();
        XmlPath document = new XmlPath(XmlPath.CompatibilityMode.HTML, responseRepresentation);
        String reportTitle = document.getString("html.body.section.find()");
        Assertions.assertThat(reportTitle).contains(expectedValue);
    }
}
