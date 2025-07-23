package belejki.com.mvc.wish.web;

import belejki.com.mvc.friendship.web.view.FriendshipViewModel;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.wish.service.FriendWishlistService;
import belejki.com.mvc.wish.web.view.WishViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user/friends")
public class FriendWishController {


	private final FriendWishlistService friendWishlistService;
	private final UserSessionInformation userinfo;

	@Autowired
	public FriendWishController(FriendWishlistService friendWishlistService, UserSessionInformation userInformation) {
		this.friendWishlistService = friendWishlistService;
		this.userinfo = userInformation;
	}


	@GetMapping("/wishlist")
	public String getFriendWishlistPage(@ModelAttribute("friend") FriendshipViewModel friendshipViewModel,
	                                    Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (!model.containsAttribute("wishlist")) {
			List<WishViewModel> wishlist = friendWishlistService.findFriendWishlist(friendshipViewModel.getFriendUsername());
			model.addAttribute("wishlist", wishlist);
		}

		model.addAttribute("friendshipViewModel", friendshipViewModel);

		return "friend_wishlist";

	}

	@GetMapping("/wishlist/filter")
	public String filterFriendWishlistByPrice(@RequestParam("maxPrice") Long maxPrice,
	                                          @RequestParam("friendUsername") String friendUsername,
	                                          RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishViewModel> wishlist = friendWishlistService.getFriendWishlistFilteredByPrice(maxPrice, friendUsername);

		FriendshipViewModel friendshipViewModel = new FriendshipViewModel();
		friendshipViewModel.setFriendUsername(friendUsername);

		redirectAttributes.addFlashAttribute("wishlist", wishlist);
		redirectAttributes.addFlashAttribute("friend", friendshipViewModel);

		return "redirect:/user/friends/wishlist";

	}


}
