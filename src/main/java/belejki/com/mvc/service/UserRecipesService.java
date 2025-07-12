package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.model.view.RecipeViewModel;

import java.util.List;

public interface UserRecipesService {
	RecipeViewModel save(UserRecipeBindingModel recipe);

	List<RecipeViewModel> searchByNameContaining(String searchValue);

	List<RecipeViewModel> searchByIngredients(List<String> ingredients);

	RecipeViewModel deleteRecipeById(Long id);
}
