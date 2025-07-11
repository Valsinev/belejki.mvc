package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.RecipeDto;

import java.util.List;

public interface FriendRecipeService {


	List<RecipeDto> getFriendRecipesByTitle(String searchValue, String username);

	List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username);
}
