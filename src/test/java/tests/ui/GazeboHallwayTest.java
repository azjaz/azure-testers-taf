package tests.ui;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.File;
import java.util.List;

class GazeboHallwayTest extends BaseTest{
    @Test
    void checkPresenceOfDownloadedReport() {
        File downloadedFileFromPage = openPage().goToDownloadReportPage().findDownloadedFile();
        Assertions.assertThat(downloadedFileFromPage).isFile();
        Assertions.assertThat(FileUtils.deleteFilesInDirectory()).isTrue();
    }

    @Test
    void checkPriceWithTaxSumOnPizzaList() {
        List<List<String>> elementsFromPizzas = openPage().goToManagePizzasPage().getListOfPizzasOnPage();
        SoftAssertions assertions = new SoftAssertions();

        for (List<String> element: elementsFromPizzas) {
            Double sumOnPage = Double.parseDouble(element.get(3));
            Double sumCounted = element.stream()
                    .skip(1)
                    .mapToDouble(Double::parseDouble)
                    .limit(2)
                            .sum();
            assertions.assertThat(sumOnPage).isEqualTo(sumCounted);
        }
        assertions.assertAll();
    }
}
