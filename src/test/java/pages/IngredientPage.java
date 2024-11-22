package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class IngredientPage extends AbstractPage {

    @FindBy(xpath = "//div[contains(@class, 'content')]//h1[contains(@class, 'title')]")
    private List<WebElement> ingredientItems;

    @FindBy(xpath = "//h1[contains(text(), 'Our Ingredients')]")
    private WebElement ingredientsTitle;

    public List<String> getIngredientsItems() {
        waitVisibility(ingredientsTitle);
        return ingredientItems.stream().map(WebElement::getText).collect(Collectors.toList());
    }

}
