package tests.ui;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

public class IsogonJoistImageTest extends BaseTest {

    @Test
    void checkNewPizzaPresenceAfterAdding() {
        String pizzaName = testString;
        String newAddedPizzaName = openPage()
                .goToAddPizzaPage()
                .addPizzaWithImage(pizzaName)
                .goToSettingsPage()
                .increaseNumberOfItemsPerPage()
                .getLastAddedPizzaName(pizzaName);

        Assertions.assertThat(newAddedPizzaName).isEqualTo(pizzaName);
    }

    @Test
    void checkNewPizzaImagePresentAfterAdding() {
        String pizzaName = testString;
        WebElement newAddedPizzaImage = openPage()
                .goToAddPizzaPage()
                .addPizzaWithImage(pizzaName)
                .goToSettingsPage()
                .increaseNumberOfItemsPerPage()
                .getLastAddedPizzaImage(pizzaName);

        Assertions.assertThat(newAddedPizzaImage).isNotNull();
    }

    @Test
    void checkUpdatePizzaImage() {
        String pizzaName = expectedPizzaItemsFirstPage.get(2);
        String newAddedPizzaImageName =
                openPage()
                .goToManagePizzasPage()
                .updatePizzaImage(pizzaName)
                .getUpdatedPizzaImageName(pizzaName);

        String initialPizzaImgName = "001";
        Assertions.assertThat(newAddedPizzaImageName).isNotEqualTo(initialPizzaImgName);
    }
}
