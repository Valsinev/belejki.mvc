package belejki.com.mvc.recipe.web;

import belejki.com.mvc.recipe.web.view.RecipeViewModel;
import belejki.com.mvc.shared.SearchBindingModel;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.recipe.service.FriendRecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@GetMapping("/recipes/{friendUsername}")
	public String getFriendRecipesPage(@PathVariable String friendUsername, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (!model.containsAttribute("searchBindingModel")) {
			model.addAttribute("searchBindingModel", new SearchBindingModel());
		}

		model.addAttribute("friendUsername", friendUsername);

		return "friend_recipes";
	}

	@GetMapping("/recipes/search/title")
	public String searchByRecipeTitle(@Valid @ModelAttribute SearchBindingModel searchBindingModel,
	                                  BindingResult bindingResult,
	                                  @RequestParam String friendUsername,
	                                  RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("searchBindingModel", searchBindingModel);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.searchBindingModel", bindingResult);
			return "redirect:/user/friends/recipes/" + friendUsername;
		}

		List<RecipeViewModel> recipes = userFriendsService.getFriendRecipesByTitle(searchBindingModel.getSearchValue(), friendUsername);

		if (recipes.isEmpty()) {
			redirectAttributes.addFlashAttribute("recipeNotFoundMessage", "{recipe.not.found}");
			return "redirect:/user/friends/recipes/" + friendUsername;
		}

		redirectAttributes.addFlashAttribute("recipes", recipes);

		return "redirect:/user/friends/recipes/" + friendUsername;
	}


	@GetMapping("/recipes/search/ingredients")
	public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients,
	                                  @RequestParam("friendUsername") String friendUsername,
	                                  RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<RecipeViewModel> recipes = userFriendsService.getFriendRecipesByIngredients(ingredients, friendUsername);

		redirectAttributes.addFlashAttribute("recipes", recipes);

		return "redirect:/user/friends/recipes/" + friendUsername;
	}

}
