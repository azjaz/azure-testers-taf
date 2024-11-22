package tests.ui;

import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import tests.ui.BaseTest;

import java.util.List;

public class AtriumSmokeTest extends BaseTest {

    private final List<String> expectedPizzaItemsFirstPage = List.of("Quattro Formaggi", "Capricciosa",
            "Hawaiian", "Rustica", "Margherita", "Pugliese");

    private final List<String> expectedIngredientsItems = List.of("Pepperoni", "Mushrooms",
            "Onions", "Bell Peppers", "Olives", "Pineapple", "Ham", "Sausage", "Bacon", "Jalapenos", "Tomatoes");

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
}
