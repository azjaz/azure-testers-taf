package models.function.hallway;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import models.function.egress.IngredientRS;
import models.function.facade.FacadeRegionModelRS;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class HallwayBulkModelRS {
    double totalPrice;
    double price;
    double tax;
    int taxRate;
    FacadeRegionModelRS[] bulkIngredients;
}
