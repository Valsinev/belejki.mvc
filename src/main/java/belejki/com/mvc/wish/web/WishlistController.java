package belejki.com.mvc.wish.web;

import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.wish.service.WishService;
import belejki.com.mvc.wish.web.binding.WishBindingModel;
import belejki.com.mvc.wish.web.view.WishViewModel;
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
@RequestMapping("/user/wishes")
public class WishlistController {

	private final WishService wishService;
	private final UserSessionInformation userinfo;

	@Autowired
	public WishlistController(WishService wishService, UserSessionInformation userinfo) {
		this.wishService = wishService;
		this.userinfo = userinfo;
	}

	@GetMapping
	public String getUserWishlist(Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishViewModel> wishlist = wishService.getWishlist();

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";

	}

	@GetMapping("/create")
	public String GetWishForm(Model model) {
		if (!model.containsAttribute("wishBindingModel")) {
			model.addAttribute("wishBindingModel", new WishBindingModel());
		}
		return "create_wish";
	}

	@PostMapping("/create")
	public String createWish(@Valid @ModelAttribute("wishBindingModel") WishBindingModel wishBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("editing", false);
			redirectAttributes.addFlashAttribute("wishBindingModel", wishBindingModel);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.wishBindingModel", bindingResult);
			return "redirect:/user/wishes/create";
		}

		wishService.createWish(wishBindingModel);

		return "redirect:/user/wishes";
	}

	@GetMapping("/edit/{id}")
	public String editWish(@PathVariable Long id, RedirectAttributes redirectAttributes) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		WishViewModel byId = wishService.findById(id);

		redirectAttributes.addFlashAttribute("wishBindingModel", byId);
		redirectAttributes.addFlashAttribute("editing", true);

		return "redirect:/user/wishes/create";

	}

	@PostMapping("/update")
	public String updateWish(@Valid @ModelAttribute("wishBindingModel") WishBindingModel wishBindingModel,
	                         BindingResult result,
	                         RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("editing", true);
			return "redirect:/user/wishes/create";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		wishService.updateWish(wishBindingModel);

		return "redirect:/user/wishes";
	}


	@GetMapping("/delete/{id}")
	public String deleteWish(@PathVariable Long id) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			wishService.deleteWish(id);

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

		List<WishViewModel> wishlist = wishService.searchByNameContaining(searchValue);

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";
	}

	@GetMapping("/filter")
	public String filterByPriceLessThan(@RequestParam("maxPrice") Long maxPrice, Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		List<WishViewModel> wishlist = wishService.filterByPriceLessThan(maxPrice);

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";
	}
}
