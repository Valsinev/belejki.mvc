package belejki.com.mvc.controller;

import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.service.UserFriendsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user/friends")
public class FriendsController {

	private final UserFriendsService userFriendsService;

	@Autowired
	public FriendsController(UserFriendsService userFriendsService) {

		this.userFriendsService = userFriendsService;
	}


	@GetMapping
	public String getUserFriends(Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";
		List<FriendshipDto> friends = userFriendsService.getFriends(token);
		model.addAttribute("friends", friends);
		return "user_friends";
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

		List<UserRecipeBindingModel> recipes = userFriendsService.getFriendRecipesByTitle(searchValue, username, token);

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

		List<UserRecipeBindingModel> recipes = userFriendsService.getFriendRecipesByIngredients(ingredients, username, token);

		model.addAttribute("recipes", recipes);
		model.addAttribute("username", username);

		return "friend_recipes";
	}

	@GetMapping("/wishlist/filter")
	public String filterFriendWishlistByPrice(@RequestParam("maxPrice") Long maxPrice,
	                                          @RequestParam("username") String username,
	                                          Model model,
	                                          HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		List<WishDto> wishlist = userFriendsService.getFriendWishlistFilteredByPrice(maxPrice, username, token);

		model.addAttribute("wishlist", wishlist);
		model.addAttribute("username", username);

		return "friend_wishlist";

	}


	@GetMapping("/wishlist/{username}")
	public String getFriendWishlistPage(@PathVariable String username, Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		List<WishDto> wishlist = userFriendsService.prepareFriendWishlistPage(username, token);

		model.addAttribute("wishlist", wishlist);
		model.addAttribute("username", username);

		return "friend_wishlist";

	}


	@PostMapping
	public String addFriend(@RequestParam("friend") String friendEmail, Model model, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			userFriendsService.addFriend(friendEmail, token);
		} catch (HttpClientErrorException.Conflict ex) {  // HTTP 409 Conflict for duplicates
			model.addAttribute("errorMessage", "Failed to add friend, maybe is already in the friendlist or does not exist.");
			return "user_friends";  // Show friends page with error message
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			model.addAttribute("errorMessage", "Failed to add friend, maybe is already in the friendlist or does not exist.");
			return "user_friends";
		}

		return "redirect:/user/friends";
	}

	@GetMapping("/remove/{id}")
	public String removeFriend(@PathVariable Long id, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			userFriendsService.removeFriend(id, token);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/friends?error=delete";
		}
		return "redirect:/user/friends";
	}

}


