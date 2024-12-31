package pages;

import org.openqa.selenium.By;
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

    @FindBy(xpath = "//a[contains(text(), 'Site Settings')]")
    private WebElement settingsMenuButton;

    @FindBy(xpath = "//a[contains(text(), 'Manage Ingredients')]/../ul/li//a[contains(text(), 'Add new')]")
    private WebElement addNewIngredientMenuButton;

    @FindBy(xpath = "//a[contains(text(), 'page')]")
    private WebElement pagination;

    @FindBy(xpath = "//a[contains(text(), 'Download report')]")
    private WebElement downloadReportButton;

    @FindBy(xpath = "//a[contains(text(), 'Manage Pizzas')]")
    private WebElement managePizzasButton;

    private String xpathToPizzaPrice = "//h1[contains(text(), '%s')]/..//i[contains(text(), 'Price')]";

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

    public SettingsPage goToSettingsPage() {
        waitVisibility(settingsMenuButton).click();
        return new SettingsPage();
    }

    public DownloadReportPage goToDownloadReportPage() {
        waitVisibility(downloadReportButton).click();
        return new DownloadReportPage();
    }

    public ManagePizzasPage goToManagePizzasPage() {
        waitVisibility(managePizzasButton).click();
        return new ManagePizzasPage();
    }

    public StartPage paginate() {
        waitVisibility(catalogTitle);
        pagination.click();
        return this;
    }

    public WebElement findPizzaPriceElement(String pizza) {
        waitVisibility(catalogTitle);
        return driver.findElement(By.xpath(String.format(xpathToPizzaPrice, pizza)));
    }
}
