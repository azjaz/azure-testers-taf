package tests.ui;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MansardNaviTest extends BaseTest{

    private final String clientName = testString;
    private final String queueBlockOrderQueue = "Orders queue (Top 10)";
    private final String queueBlockInWork = "In Work Orders";
    private final String queueBlockCompleted = "Completed Orders";
    private final String queueBlockExpired = "Expired orders (Top 10)";


    @Test
    void checkStatusOfProcessingOrder() {
        String expectedOrderStatus = "In Progress";

        String actualOrderStatus = openPage()
                .pressButtonOrderPizza(expectedPizzaItemsFirstPage.get(1))
                .sendOrder(clientName, String.valueOf(testNumber))
                .takeOrderInWork(queueBlockOrderQueue, clientName)
                .getStatusOfProceedOrder(queueBlockInWork);

        Assertions.assertThat(actualOrderStatus).contains(expectedOrderStatus);
    }

    @Test
    void checkStatusOfCompletedOrder() {
        String expectedOrderStatus = "Complete";

        String actualOrderStatus = openPage()
                .pressButtonOrderPizza(expectedPizzaItemsFirstPage.get(1))
                .sendOrder(clientName, String.valueOf(testNumber))
                .takeOrderInWork(queueBlockOrderQueue, clientName)
                .completeOrder(queueBlockInWork, clientName)
                .getStatusOfCompletedOrder(queueBlockCompleted);

        Assertions.assertThat(actualOrderStatus).contains(expectedOrderStatus);
    }

    @Test
    void checkReturnFromExpiredToInWorkOrder() {
        String expectedOrderStatus = "In Progress";

        String actualOrderStatus = openPage()
                .pressButtonOrderPizza(expectedPizzaItemsFirstPage.get(1))
                .sendOrder(clientName, String.valueOf(testNumber))
                .waitUntilOrderExpire(queueBlockExpired, clientName)
                .backInWorkExpiredOrder()
                .getStatusOfProceedOrder(queueBlockInWork);

        Assertions.assertThat(actualOrderStatus).contains(expectedOrderStatus);
    }
}
