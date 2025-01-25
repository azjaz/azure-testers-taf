package tests.ui;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class KioskLoggiaIngredientsTest extends BaseTest {

    private final String ingredient = UUID.randomUUID().toString();

    @Test
    void checkNewIngredientAfterAdding() {
        String addedIngredientFromPage = openPage().goToAddIngredientPage().addIngredient(ingredient);
        Assertions.assertThat(addedIngredientFromPage).isEqualTo(ingredient);
    }
}
