package belejki.com.mvc.recipe.web.view;

import belejki.com.mvc.recipe.recipeIngredient.RecipeIngredientViewModel;
import lombok.Data;

import java.util.List;

@Data
public class RecipeViewModel {
	private Long id;
	private String name;
	private String instructions;
	private String videoLink;
	private List<RecipeIngredientViewModel> ingredients;
}
