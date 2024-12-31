package models.function.hallway;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import models.function.egress.Ingredient;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class HallwayBulkModelRQ {

    String region;
    Ingredient[][] bulkIngredients;
}
