package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ManagePizzasPage extends AbstractPage{

    @FindBy(xpath = "//tbody//th/..")
    private List<WebElement> listOfPizzasOnPage;

    private static String xpathToPizzaData = "td[contains(text(),'.')]";

    public List<List<String>> getListOfPizzasOnPage() {

        List<List<String>> elementsOfEachPizza = new ArrayList<>();

        for(WebElement pizzaItem: listOfPizzasOnPage) {
            List<String> pizzaElements = pizzaItem.findElements(By.xpath(xpathToPizzaData))
                    .stream().map(WebElement::getText).collect(Collectors.toList());
            elementsOfEachPizza.add(pizzaElements);
        }
        return elementsOfEachPizza;
    }
}
