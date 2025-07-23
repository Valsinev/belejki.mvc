package belejki.com.mvc.recipe.repository;

import belejki.com.mvc.recipe.web.dto.RecipeDto;
import belejki.com.mvc.recipe.web.view.RecipeViewModel;

import java.util.List;

public interface FriendRecipeRepository {
	List<RecipeViewModel> getFriendRecipesByTitle(String searchValue, String username);

	List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username);
}
