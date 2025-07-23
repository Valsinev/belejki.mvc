package belejki.com.mvc.recipe.service;

import belejki.com.mvc.recipe.web.view.RecipeViewModel;

import java.util.List;

public interface FriendRecipeService {


	List<RecipeViewModel> getFriendRecipesByTitle(String searchValue, String username);

	List<RecipeViewModel> getFriendRecipesByIngredients(List<String> ingredients, String username);
}
