package belejki.com.mvc.controller;

import belejki.com.mvc.model.dto.RecipeDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.model.view.RecipeViewModel;
import belejki.com.mvc.service.FriendRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/user/friends")
public class FriendRecipeController {

	private final FriendRecipeService userFriendsService;
	private final UserSessionInformation userinfo;

	@Autowired
	public FriendRecipeController(FriendRecipeService userFriendsService, UserSessionInformation userInformation) {
		this.userFriendsService = userFriendsService;
		this.userinfo = userInformation;
	}

	@GetMapping("/recipes/{username}")
	public String getFriendRecipesPage(@PathVariable String username, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		model.addAttribute("username", username);

		return "friend_recipes";
	}

	@GetMapping("/recipes/search/title")
	public String searchByRecipeTitle(@RequestParam String searchValue,
	                                  @RequestParam String username,
	                                  Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<RecipeViewModel> recipes = userFriendsService.getFriendRecipesByTitle(searchValue, username);

		model.addAttribute("recipes", recipes);
		model.addAttribute("username", username);


		return "friend_recipes";
	}


	@GetMapping("/recipes/search/ingredients")
	public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients,
	                                  @RequestParam("username") String username,
	                                  Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<RecipeViewModel> recipes = userFriendsService.getFriendRecipesByIngredients(ingredients, username);

		model.addAttribute("recipes", recipes);
		model.addAttribute("username", username);

		return "friend_recipes";
	}

}
