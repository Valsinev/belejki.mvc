package belejki.com.mvc.recipe.recipeIngredient;

import belejki.com.mvc.recipe.ingredient.IngredientDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto {

    private Long id;
    private Long recipeId;
    private IngredientDto ingredient;
    private String quantity;

}
