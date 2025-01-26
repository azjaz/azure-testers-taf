package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CompletedOrderPage extends AbstractPage {

    @FindBy(xpath = "//a[contains(text(), 'Completed orders')]")
    private WebElement completedOrdersButton;

    public String getStatusOfCompletedOrder(String queueBlockCompleted) {
        waitVisibility(completedOrdersButton).click();
        return waitVisibility(driver.findElements(By.xpath(String.format(xpathToOrderStatuses, queueBlockCompleted))).stream().reduce((a, b) -> b).get()).getText();
    }
}
