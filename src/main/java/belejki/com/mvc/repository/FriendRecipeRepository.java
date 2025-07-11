package belejki.com.mvc.repository;

import belejki.com.mvc.model.dto.RecipeDto;

import java.util.List;

public interface FriendRecipeRepository {
	List<RecipeDto> getFriendRecipesByTitle(String searchValue, String username);

	List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username);
}
