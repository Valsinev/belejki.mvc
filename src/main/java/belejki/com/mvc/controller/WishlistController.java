package belejki.com.mvc.controller;

import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;
import belejki.com.mvc.service.UserWishlistService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

	@Autowired
	public WishlistController(UserWishlistService userWishlistService) {
		this.userWishlistService = userWishlistService;
	}

	@GetMapping
	public String getUserWishlist(Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		List<WishDto> wishlist = userWishlistService.getWishlist(token);

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";

	}

	@GetMapping("/create")
	public String GetWishForm(Model model) {
		model.addAttribute("wish", new WishDto());
		return "create_wish";
	}

	@PostMapping("/create")
	public String createWish(@Valid @ModelAttribute("wish") UserWishBindingModel wish,
	                         BindingResult result,
	                         HttpSession session,
	                         Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editing", false);
			return "create_wish";
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		userWishlistService.createWish(wish, token);

		return "redirect:/user/wishes";
	}

	@GetMapping("/edit/{id}")
	public String editWish(@PathVariable Long id,
	                       HttpSession session,
	                       Model model) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		WishDto wish = userWishlistService.editWish(id, token);

		model.addAttribute("wish", wish);
		model.addAttribute("editing", true);

		return "create_wish";

	}

	@PostMapping("/update/{id}")
	public String updateWish(@PathVariable Long id,
	                         @Valid @ModelAttribute("wish") UserWishBindingModel wish,
	                         BindingResult result,
	                         HttpSession session,
	                         Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editing", true);
			return "create_wish";
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		userWishlistService.updateWish(id, wish, token);

		return "redirect:/user/wishes";
	}


	@GetMapping("/delete/{id}")
	public String deleteWish(@PathVariable Long id, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			userWishlistService.deleteWish(id, token);

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/wishes?error=delete";
		}
		return "redirect:/user/wishes";

	}

	@GetMapping("/search")
	public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
	                                     Model model,
	                                     HttpSession session) {
		if (searchValue == null || searchValue.trim().isEmpty()) {
			model.addAttribute("error", "Search value cannot be empty");
			return "redirect:/user/wishes"; // or your actual view name
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		List<WishDto> wishlist = userWishlistService.searchByNameContaining(searchValue, token);

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";
	}

	@GetMapping("/filter")
	public String filterByPriceLessThan(@RequestParam("maxPrice") Long maxPrice,
	                                    Model model,
	                                    HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		List<WishDto> wishlist = userWishlistService.filterByPriceLessThan(maxPrice, token);

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";
	}
}
