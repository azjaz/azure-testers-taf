package tests.ui;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EgressFacadePriceTest extends BaseTest {

    @Test
    void checkPresenceOfPriceElementOnFirstPage() {
        Assertions.assertThat(openPage()
                .findPizzaPriceElement(expectedPizzaItemsFirstPage.get(2)))
                .isNotNull();
    }

    @Test
    void checkPresenceOfPriceElementOnSecondvPage() {
        Assertions.assertThat(openPage()
                .paginate()
                .findPizzaPriceElement(expectedPizzaItemsSecondPage.get(2)))
                .isNotNull();
    }
}
