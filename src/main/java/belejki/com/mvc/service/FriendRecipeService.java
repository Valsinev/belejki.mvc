package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.RecipeDto;
import belejki.com.mvc.model.view.RecipeViewModel;

import java.util.List;

public interface FriendRecipeService {


	List<RecipeViewModel> getFriendRecipesByTitle(String searchValue, String username);

	List<RecipeViewModel> getFriendRecipesByIngredients(List<String> ingredients, String username);
}
