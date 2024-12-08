package tests.ui;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

public class AtriumSmokeTest extends BaseTest {

    private final List<String> expectedPizzaItemsFirstPage = List.of("Quattro Formaggi", "Capricciosa",
            "Hawaiian", "Rustica", "Margherita", "Pugliese");

    private final List<String> expectedIngredientsItems = List.of("Pepperoni", "Mushrooms",
            "Onions", "Bell Peppers", "Olives", "Pineapple", "Ham", "Sausage", "Bacon", "Jalapenos", "Tomatoes");

    private final String ingredient = RandomStringUtils.random(8, true, false);

    private final int numberOfColumns = new Random().ints(3, 9).findFirst().getAsInt();

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
        int numberOfColumnsOnPage = openPage().goToSettingsPage().changeNumberOfColumns(numberOfColumns);
        Assertions.assertThat(numberOfColumnsOnPage).isEqualTo(numberOfColumns);
    }
}
