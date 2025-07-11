package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;

import java.util.List;

public interface UserRecipesService {
	RecipeDto save(UserRecipeBindingModel recipe);

	List<RecipeDto> searchByNameContaining(String searchValue);

	List<RecipeDto> searchByIngredients(List<String> ingredients);

	RecipeDto deleteRecipeById(Long id);
}
