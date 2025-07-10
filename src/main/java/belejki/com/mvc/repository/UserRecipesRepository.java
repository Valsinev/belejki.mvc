package belejki.com.mvc.repository;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;

import java.util.List;

public interface UserRecipesRepository {

	RecipeDto save(RecipeDto recipe);

	List<RecipeDto> findAllByNameContaining(String searchValue);

	List<RecipeDto> findAllByIngredients(List<String> ingredients);

	RecipeDto deleteById(Long id);
}
