package belejki.com.mvc.repository;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;

import java.util.List;

public interface UserRecipesRepository {
	RecipeDto save(RecipeDto recipe, String jwtToken);

	List<RecipeDto> findAllByNameContaining(String searchValue, String jwtToken);

	List<RecipeDto> findAllByIngredients(List<String> ingredients, String jwtToken);

	RecipeDto deleteById(Long id, String jwtToken);
}
