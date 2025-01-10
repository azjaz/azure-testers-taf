package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ManagePizzasPage extends AbstractPage {

    @FindBy(xpath = "//tbody//th/..")
    private List<WebElement> listOfPizzasOnPage;

    @FindBy(xpath = "//a[contains(text(), 'Pizza Catalog')]")
    private WebElement pizzaCatalogButton;

    @FindBy(xpath = "//label/input[@type='file']")
    private WebElement updateImageInput;


    private static String xpathToPizzaData = "td[contains(text(),'.')]";
    private static String xpathToUpdatePizzaButton = "//a[contains(@data-title, '%s') and contains(text(), 'Update image')]";
    private static String xpathToPizzaImageInCatalog = "//h1[contains(text(), '%s')]/../../..//img[contains(@src, '')]";
    private static String inputFromModalWindow = "//label/input[@type='file']";
    private static String submitButtonFromModalWindow = "//button[contains(text(), 'Submit')]";
    private static String exitButtonFromModalWindow = "//button[@class='modal-close is-large']";

    public List<List<String>> getListOfPizzasOnPage() {

        List<List<String>> elementsOfEachPizza = new ArrayList<>();

        for (WebElement pizzaItem : listOfPizzasOnPage) {
            List<String> pizzaElements = pizzaItem.findElements(By.xpath(xpathToPizzaData))
                    .stream().map(WebElement::getText).collect(Collectors.toList());
            elementsOfEachPizza.add(pizzaElements);
        }
        return elementsOfEachPizza;
    }

    public ManagePizzasPage updatePizzaImage(String pizzaName) {
        waitVisibility(driver.findElement(By.xpath(String.format(xpathToUpdatePizzaButton, pizzaName)))).click();

        driver.findElement(By.xpath(inputFromModalWindow)).sendKeys(imgFile.getAbsolutePath());
        driver.findElements(By.xpath(submitButtonFromModalWindow)).get(1).click();
        driver.findElements(By.xpath(exitButtonFromModalWindow)).get(1).sendKeys(Keys.ESCAPE);

        waitVisibility(pizzaCatalogButton).click();
        return this;
    }

    public String getUpdatedPizzaImageName(String pizzaName) {
        String pathToImage = waitVisibility(driver.findElement(By.xpath(String.format(xpathToPizzaImageInCatalog, pizzaName)))).getAttribute("src");
        return Arrays.stream(Arrays.stream(pathToImage.split("/"))
                        .filter(a -> a.contains(".png"))
                        .findFirst()
                        .get().split("\\."))
                .limit(1)
                .findFirst()
                .get();

    }
}
