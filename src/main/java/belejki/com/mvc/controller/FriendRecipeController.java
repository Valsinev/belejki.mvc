package belejki.com.mvc.controller;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.service.FriendRecipeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user/friends")
public class FriendRecipeController {

	private final FriendRecipeService userFriendsService;

	@Autowired
	public FriendRecipeController(FriendRecipeService userFriendsService) {
		this.userFriendsService = userFriendsService;
	}

	@GetMapping("/recipes/{username}")
	public String getFriendRecipesPage(@PathVariable String username, Model model, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";
		model.addAttribute("username", username);

		return "friend_recipes";
	}

	@GetMapping("/recipes/search/title")
	public String searchByRecipeTitle(@RequestParam String searchValue,
	                                  @RequestParam String username,
	                                  Model model,
	                                  HttpSession session) {

		String token = (String) session.getAttribute("jwt");

		if (token == null) return "redirect:/login";

		List<RecipeDto> recipes = userFriendsService.getFriendRecipesByTitle(searchValue, username, token);

		model.addAttribute("recipes", recipes);
		model.addAttribute("username", username);


		return "friend_recipes";
	}


	@GetMapping("/recipes/search/ingredients")
	public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients,
	                                  @RequestParam("username") String username,
	                                  Model model,
	                                  HttpSession session,
	                                  Locale locale) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		List<RecipeDto> recipes = userFriendsService.getFriendRecipesByIngredients(ingredients, username, token);

		model.addAttribute("recipes", recipes);
		model.addAttribute("username", username);

		return "friend_recipes";
	}

}
