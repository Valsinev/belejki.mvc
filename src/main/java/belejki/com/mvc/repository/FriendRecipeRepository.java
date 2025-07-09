package belejki.com.mvc.repository;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;

import java.util.List;

public interface FriendRecipeRepository {
	List<RecipeDto> getFriendRecipesByTitle(String searchValue, String username, String jwtToken);

	List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username, String jwtToken);
}
