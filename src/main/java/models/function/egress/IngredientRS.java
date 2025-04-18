package models.function.egress;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class IngredientRS {
    String name;
    Integer quantity;
    double price;
}
