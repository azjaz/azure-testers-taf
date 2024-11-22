package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class StartPage extends AbstractPage {

    @FindBy(xpath = "//div[contains(@class, 'content')]/h1[contains(@class, 'title')]")
    private List<WebElement> catalogItems;

    @FindBy(xpath = "//h1[contains(text(), 'Catalog')]")
    private WebElement catalogTitle;

    @FindBy(xpath = "//a[contains(text(), 'Our Ingredients')]")
    private WebElement ingredientMenuButton;

    @FindBy(xpath = "//a[contains(text(), 'Manage Ingredients')]/../ul/li//a[contains(text(), 'Add new')]")
    private WebElement addNewIngredientMenuButton;

    public List<String> getCatalogItems() {
        waitVisibility(catalogTitle);
        return catalogItems.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public IngredientPage goToIngredientPage() {
        waitVisibility(ingredientMenuButton).click();
        return new IngredientPage();
    }

    public AddIngredientPage goToAddIngredientPage() {
        waitVisibility(addNewIngredientMenuButton).click();
        return new AddIngredientPage();
    }
}
