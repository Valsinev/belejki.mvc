package belejki.com.mvc.controller;

import belejki.com.mvc.model.binding.SearchBindingModel;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.model.view.FriendshipViewModel;
import belejki.com.mvc.service.UserFriendsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user/friends")
public class UserFriendController {

	private final UserFriendsService userFriendsService;
	private final UserSessionInformation userinfo;

	@Autowired
	public UserFriendController(UserFriendsService userFriendsService, UserSessionInformation userinfo) {

		this.userFriendsService = userFriendsService;
		this.userinfo = userinfo;
	}


	@GetMapping
	public String getUserFriends(Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (!model.containsAttribute("searchBindingModel")) {
			model.addAttribute("searchBindingModel", new SearchBindingModel());
		}

		// ✅ Only load all friends if not already present in model
		if (!model.containsAttribute("friends")) {
			List<FriendshipViewModel> friends = userFriendsService.getFriends();
			model.addAttribute("friends", friends);
		}

		return "user_friends";
	}


	@PostMapping
	public String addFriend(@RequestParam("friend") String friendEmail, RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			userFriendsService.addFriend(friendEmail);
		} catch (HttpClientErrorException.Conflict ex) {  // HTTP 409 Conflict for duplicates
			redirectAttributes.addFlashAttribute("errorMessage", "{friend.is.already.in.the.friendlist.or.does.not.exist}");
			return "redirect:/user/friends";  // Show friends page with error message
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "{friend.is.already.in.the.friendlist.or.does.not.exist}");
			return "redirect:/user/friends";
		}

		return "redirect:/user/friends";
	}

	@GetMapping("/search")
	public String findFriend(@Valid @ModelAttribute("searchBindingModel") SearchBindingModel searchBindingModel,
	                         BindingResult bindingResult,
	                         RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("searchBindingModel", searchBindingModel);
			return "redirect:/user/friends";
		}

		List<FriendshipViewModel> friends = userFriendsService.findAllByFirstName(searchBindingModel.getSearchValue());

		redirectAttributes.addFlashAttribute("friends", friends);
		return "redirect:/user/friends";

	}

	@GetMapping("/remove/{id}")
	public String removeFriend(@PathVariable Long id) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			userFriendsService.removeFriend(id);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/friends?error=delete";
		}
		return "redirect:/user/friends";
	}

}


