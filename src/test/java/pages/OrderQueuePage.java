package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class OrderQueuePage extends AbstractPage {

    private String xpathToOrders = "//h1[contains(text(), '%s')]/..//ul/li[text()='Client name: %s']";
    private String xpathToOrderProcessingButton = "//a[contains(text(), '%s')]";
    private String xpathToCompleteOrderLink = "//h1[contains(text(), '%s')]/..//a[contains(text(), 'Complete')]";

    @FindBy(xpath = "//h1[contains(text(), 'In Work Orders')]/..//tbody//ul/li")
    private List<WebElement> listOfOrderAttributes;

    private final long EXPIRY_TIME = 61;

    public OrderQueuePage takeOrderInWork(String queueBlockInQueue, String clientName) {
        WebElement orderBlock = waitVisibility(driver.findElements(By.xpath(String.format(xpathToOrders, queueBlockInQueue, clientName))).stream().reduce((a, b) -> b).get());
        waitVisibility(orderBlock.findElements(By.xpath(String.format(xpathToOrderProcessingButton, "Take In Work"))).stream().reduce((a, b) -> b).get()).click();
        return this;
    }

    public String getStatusOfProceedOrder(String queueBlockInWork) {
        return waitVisibility(driver.findElements(By.xpath(String.format(xpathToOrderStatuses, queueBlockInWork))).stream().reduce((a, b) -> b).get()).getText();
    }

    public CompletedOrderPage completeOrder(String queueBlockInWork, String clientName) {
        WebElement orderBlock = waitVisibility(driver.findElements(By.xpath(String.format(xpathToOrders, queueBlockInWork, clientName))).stream().reduce((a, b) -> b).get());
        waitVisibility(orderBlock.findElements(By.xpath(String.format(xpathToCompleteOrderLink, queueBlockInWork)))
                .stream().reduce((a, b) -> b).get()).click();
        return new CompletedOrderPage();
    }

    public OrderQueuePage waitUntilOrderExpire(String queueBlockExpired, String clientName) {
        waitVisibility(driver.findElements(By.xpath(String.format(xpathToOrders, "Orders queue (Top 10)", clientName))).stream().reduce((a, b) -> b).get());
        implicitWait(EXPIRY_TIME);
        driver.navigate().refresh();
        return this;
    }

    public OrderQueuePage backInWorkExpiredOrder() {
        waitVisibility(driver.findElements(By.xpath(String.format(xpathToOrderProcessingButton, "Back In Work")))
                .stream().reduce((a, b) -> b).get())
                .click();
        return this;
    }

    public List<String> getOrderAttributes() {
       return listOfOrderAttributes
                .stream()
                .map(element -> element
                        .getText()
                        .split(":")[0])
                .distinct()
                .collect(Collectors.toList());
    }
}
