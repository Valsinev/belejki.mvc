package belejki.com.mvc.recipe.web.dto;

import belejki.com.mvc.recipe.recipeIngredient.RecipeIngredientDto;
import belejki.com.mvc.recipe.recipeIngredient.RecipeIngredientViewModel;
import belejki.com.mvc.recipe.web.view.RecipeViewModel;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {

	private Long id;
	private Long userId;
	private String name;
	private String instructions;
	private String videoLink;
	private List<RecipeIngredientViewModel> ingredients;
}
