package constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServiceConstants {
    PATH_TO_DRIVER_PROPERTY_FILE("src/test/resources/driver.properties"),
    API("api/"),
    PORT(":3000/"),
    FUNCTION("GetPrice"),
    PATH_TO_DOWNLOAD_FOLDER("C://Downloads"),
    PATH_TO_IMAGE_FILE("src/test/resources/img/testPizza"),
    PATH_TO_INGREDIENTS("our_ingredients");


    private final String value;


    }
