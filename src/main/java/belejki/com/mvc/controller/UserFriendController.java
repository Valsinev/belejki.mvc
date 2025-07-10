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
public class UserFriendController {

	private final UserFriendsService userFriendsService;

	@Autowired
	public UserFriendController(UserFriendsService userFriendsService) {

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

	@GetMapping("/search")
	public String findFriend(@RequestParam("searchValue") String searchValue, HttpSession session, Model model) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		List<FriendshipDto> friends = userFriendsService.findAllByFirstName(searchValue, token);

		model.addAttribute("friends", friends);
		return "user_friends";

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


