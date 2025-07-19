package belejki.com.mvc.repository;

import belejki.com.mvc.model.dto.RecipeDto;
import belejki.com.mvc.model.view.RecipeViewModel;

import java.util.List;

public interface FriendRecipeRepository {
	List<RecipeViewModel> getFriendRecipesByTitle(String searchValue, String username);

	List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username);
}
