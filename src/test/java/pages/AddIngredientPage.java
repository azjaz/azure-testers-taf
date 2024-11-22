package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddIngredientPage extends AbstractPage {

    @FindBy(xpath = "//h1[contains(text(), 'Add Ingredient')]")
    private WebElement addIngredientTitle;

    @FindBy(xpath = "//h1[contains(text(), 'Ingredients list')]")
    private WebElement listIngredientTitle;

    @FindBy(xpath = "//button[contains(text(), 'Submit')]")
    private WebElement submitButton;

    @FindBy(xpath = "//label[contains(text(), 'Title')]/../div/input")
    private WebElement ingredientNameInput;

    @FindBy(xpath = "//label[contains(text(), 'Description')]/../div/textarea")
    private WebElement ingredientDescriptionInput;

    String xpathOutput = "//tbody/tr/td[contains(text(), '%s')]";

    public String addIngredient(String ingredient) {
        waitVisibility(addIngredientTitle);
        ingredientNameInput.sendKeys(ingredient);
        ingredientDescriptionInput.sendKeys(ingredient);
        submitButton.click();
        waitVisibility(listIngredientTitle);

        return driver.findElement(By.xpath(String.format(xpathOutput, ingredient))).getText();
    }

}
