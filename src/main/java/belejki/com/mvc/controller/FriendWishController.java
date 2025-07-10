package belejki.com.mvc.controller;

import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.service.FriendWishlistService;
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
public class FriendWishController {


	private final FriendWishlistService userFriendsService;
	private final UserSessionInformation userinfo;

	@Autowired
	public FriendWishController(FriendWishlistService userFriendsService, UserSessionInformation userInformation) {
		this.userFriendsService = userFriendsService;
		this.userinfo = userInformation;
	}

	@GetMapping("/wishlist/filter")
	public String filterFriendWishlistByPrice(@RequestParam("maxPrice") Long maxPrice,
	                                          @RequestParam("username") String username,
	                                          Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishDto> wishlist = userFriendsService.getFriendWishlistFilteredByPrice(maxPrice, username);

		model.addAttribute("wishlist", wishlist);
		model.addAttribute("username", username);

		return "friend_wishlist";

	}


	@GetMapping("/wishlist/{username}")
	public String getFriendWishlistPage(@PathVariable String username, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishDto> wishlist = userFriendsService.prepareFriendWishlistPage(username);

		model.addAttribute("wishlist", wishlist);
		model.addAttribute("username", username);

		return "friend_wishlist";

	}


}
