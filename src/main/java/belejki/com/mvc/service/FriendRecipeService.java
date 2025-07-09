package belejki.com.mvc.service;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;

import java.util.List;

public interface FriendRecipeService {


	List<RecipeDto> getFriendRecipesByTitle(String searchValue, String username, String jwtToken);

	List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username, String jwtToken);
}
