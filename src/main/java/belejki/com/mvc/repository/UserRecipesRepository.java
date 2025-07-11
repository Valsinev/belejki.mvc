package belejki.com.mvc.repository;

import belejki.com.mvc.model.dto.RecipeDto;

import java.util.List;

public interface UserRecipesRepository {

	RecipeDto save(RecipeDto recipe);

	List<RecipeDto> findAllByNameContaining(String searchValue);

	List<RecipeDto> findAllByIngredients(List<String> ingredients);

	RecipeDto deleteById(Long id);
}
