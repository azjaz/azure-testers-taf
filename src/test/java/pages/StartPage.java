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

    @FindBy(xpath = "//a[contains(text(), 'Next page')]")
    private WebElement pagination;

    @FindBy(xpath = "//a[contains(text(), 'Download report')]")
    private WebElement downloadReportButton;

    @FindBy(xpath = "//a[contains(text(), 'Manage Pizzas')]")
    private WebElement managePizzasButton;

    @FindBy(xpath = "//a[contains(text(), 'Add new')]")
    private WebElement addNewPizzaButton;

    private String xpathToPizzaPrice = "//h1[contains(text(), '%s')]/..//i[contains(text(), 'Price')]";
    private String xpathToPizzaName = "//div[contains(@class, 'card')]/div/div/h1[contains(text(), '%s')]";
    private String xpathToPizzaImg = "//h1[contains(text(), '%s')]/../../..//img[contains(@src, '.png')]";
    private String xpathToOrderButton = "//h1[contains(text(), '%s')]/..//a[text()='Order']";


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

    public AddPizzaPage goToAddPizzaPage() {
        waitVisibility(addNewPizzaButton).click();
        return new AddPizzaPage();
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

    public String getLastAddedPizzaName(String pizzaName) {
        waitVisibility(catalogTitle);
        return driver.findElement(By.xpath(String.format(xpathToPizzaName, pizzaName))).getText();
    }

    public WebElement getLastAddedPizzaImage(String pizzaName) {
        waitVisibility(catalogTitle);
        return driver.findElement(By.xpath(String.format(xpathToPizzaImg, pizzaName)));
    }

    public OrderPage pressButtonOrderPizza(String pizza) {
        waitVisibility(driver.findElement(By.xpath(String.format(xpathToOrderButton, pizza)))).click();
        return new OrderPage();
    }
}
