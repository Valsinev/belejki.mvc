package belejki.com.mvc.recipe.repository;

import belejki.com.mvc.recipe.web.dto.RecipeDto;

import java.util.List;

public interface RecipeRepository {

	RecipeDto save(RecipeDto recipe);

	List<RecipeDto> findAllByNameContaining(String searchValue);

	List<RecipeDto> findAllByIngredients(List<String> ingredients);

	void deleteById(Long id);
}
