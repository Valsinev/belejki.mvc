package belejki.com.mvc.recipe.recipeIngredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RecipeIngredientBindingModel {

	private Long id;
	private Long recipeId;
	@NotBlank(message = "Ingredient must have name.")
	@Size(min = 2, max = 64, message = "Ingredient name must be between 2 and 64 characters.")
	private String ingredientName;
	@NotBlank(message = "Ingredient must have quantity.")
	@Size(min = 2, max = 64, message = "Ingredient quantity must be between 2 and 64 characters.")
	private String quantity;
}
