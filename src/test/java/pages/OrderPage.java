package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderPage extends AbstractPage {

    @FindBy(xpath = "//h1[contains(text(), 'Create Order')]")
    private WebElement createOrderTitle;

    @FindBy(xpath = "//input[@name='client_name']")
    private WebElement clientNameInput;

    @FindBy(xpath = "//input[@name='client_contact']")
    private WebElement clientContactInput;

    @FindBy(xpath = "//button[contains(text(), 'Send Order')]")
    private WebElement sendOrderButton;

    @FindBy(xpath = "//a[text()= 'Orders queue']")
    private WebElement ordersQueueButton;

    public OrderQueuePage sendOrder(String clientName, String clientContact) {
        waitVisibility(createOrderTitle);
        clientNameInput.sendKeys(clientName);
        clientContactInput.sendKeys(clientContact);
        sendOrderButton.click();
        waitVisibility(ordersQueueButton).click();
        return new OrderQueuePage();
    }
}
