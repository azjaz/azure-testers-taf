package tests.ui;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class AtriumSmokeTest extends BaseTest {

    private final String ingredient = RandomStringUtils.random(8, true, false);


    @Test
    void checkPresenceOfPizzaList() {
        List<String> itemsFromPage = openPage().getCatalogItems();
        Assertions.assertThat(itemsFromPage).containsAll(expectedPizzaItemsFirstPage);
    }

    @Test
    void checkPresenceOfIngredientsList() {
        List<String> ingredientsFromPage = openPage().goToIngredientPage().getIngredientsItems();
        Assertions.assertThat(ingredientsFromPage).containsAll(expectedIngredientsItems);
    }

    @Test
    void checkPresenceOfAddedIngredient() {
        String addedIngredientFromPage = openPage().goToAddIngredientPage().addIngredient(ingredient);
        Assertions.assertThat(addedIngredientFromPage).isEqualTo(ingredient);
    }

    @Test
    void checkNumberOfColumns() {
        int numberOfColumnsOnPage = openPage().goToSettingsPage().changeNumberOfColumns(testNumber);
        Assertions.assertThat(numberOfColumnsOnPage).isEqualTo(testNumber);
    }
}
