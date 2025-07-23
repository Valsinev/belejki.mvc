package belejki.com.mvc.recipe.service;

import belejki.com.mvc.recipe.web.binding.RecipeBindingModel;
import belejki.com.mvc.recipe.web.view.RecipeViewModel;

import java.util.List;

public interface RecipeService {
	RecipeViewModel save(RecipeBindingModel recipe);

	List<RecipeViewModel> searchByNameContaining(String searchValue);

	List<RecipeViewModel> searchByIngredients(List<String> ingredients);

	void deleteRecipeById(Long id);
}
