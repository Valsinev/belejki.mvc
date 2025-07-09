package belejki.com.mvc.repository;

import belejki.com.mvc.model.binding.UserRecipeBindingModel;

import java.util.List;

public interface UserRecipesRepository {
	void save(UserRecipeBindingModel recipe, String jwtToken);

	List<UserRecipeBindingModel> searchByNameContaining(String searchValue, String jwtToken);

	List<UserRecipeBindingModel> searchByIngredients(List<String> ingredients, String jwtToken);

	void deleteById(Long id, String jwtToken);
}
