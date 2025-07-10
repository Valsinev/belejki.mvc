package belejki.com.mvc.controller;

import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.service.UserWishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Controller
@RequestMapping("/user/wishes")
public class WishlistController {

	private final UserWishlistService userWishlistService;
	private final UserSessionInformation userinfo;

	@Autowired
	public WishlistController(UserWishlistService userWishlistService, UserSessionInformation userinfo) {
		this.userWishlistService = userWishlistService;
		this.userinfo = userinfo;
	}

	@GetMapping
	public String getUserWishlist(Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishDto> wishlist = userWishlistService.getWishlist();

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";

	}

	@GetMapping("/create")
	public String GetWishForm(Model model) {
		model.addAttribute("wish", new WishDto());
		return "create_wish";
	}

	@PostMapping("/create")
	public String createWish(@Valid @ModelAttribute("wish") UserWishBindingModel wish, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAttribute("editing", false);
			return "create_wish";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		userWishlistService.createWish(wish);

		return "redirect:/user/wishes";
	}

	@GetMapping("/edit/{id}")
	public String editWish(@PathVariable Long id, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		WishDto wish = userWishlistService.editWish(id);

		model.addAttribute("wish", wish);
		model.addAttribute("editing", true);

		return "create_wish";

	}

	@PostMapping("/update/{id}")
	public String updateWish(@PathVariable Long id,
	                         @Valid @ModelAttribute("wish") UserWishBindingModel wish,
	                         BindingResult result,
	                         Model model) {

		if (result.hasErrors()) {
			model.addAttribute("editing", true);
			return "create_wish";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		userWishlistService.updateWish(id, wish);

		return "redirect:/user/wishes";
	}


	@GetMapping("/delete/{id}")
	public String deleteWish(@PathVariable Long id) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			userWishlistService.deleteWish(id);

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/wishes?error=delete";
		}
		return "redirect:/user/wishes";

	}

	@GetMapping("/search")
	public String searchByNameContaining(@RequestParam("searchValue") String searchValue, Model model) {

		if (searchValue == null || searchValue.trim().isEmpty()) {
			model.addAttribute("error", "Search value cannot be empty");
			return "redirect:/user/wishes"; // or your actual view name
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishDto> wishlist = userWishlistService.searchByNameContaining(searchValue);

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";
	}

	@GetMapping("/filter")
	public String filterByPriceLessThan(@RequestParam("maxPrice") Long maxPrice, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishDto> wishlist = userWishlistService.filterByPriceLessThan(maxPrice);

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";
	}
}
