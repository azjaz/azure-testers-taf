package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddPizzaPage extends AbstractPage{
    @FindBy(xpath = "//input[contains(@type, 'text')]")
    private WebElement newPizzaTitle;

    @FindBy(xpath = "//input[contains(@type, 'file')]")
    private WebElement addImageForm;

    @FindBy(xpath = "//a[contains(text(), 'Pizza Catalog')]")
    private WebElement pizzaCatalogTab;

    public StartPage addPizzaWithImage(String pizzaName) {

        waitVisibility(newPizzaTitle).sendKeys(pizzaName);
        addImageForm.sendKeys(imgFile.getAbsolutePath());
        submitButton.click();
        waitVisibility(pizzaCatalogTab).click();
        return new StartPage();
    }
}
