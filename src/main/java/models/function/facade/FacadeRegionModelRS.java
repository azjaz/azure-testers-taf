package models.function.facade;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import models.function.egress.IngredientRS;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class FacadeRegionModelRS {
    double totalPrice;
    double price;
    double tax;
    int taxRate;
    IngredientRS[] ingredients;
}
