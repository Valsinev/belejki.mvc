package belejki.com.mvc.controller;

import belejki.com.mvc.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.service.UserShoppingItemsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Set;

@Controller
@RequestMapping("/user/shoplist")
public class ShoppingItemsController {

	private final UserShoppingItemsService userShoppingItemsService;
	private final UserSessionInformation userinfo;

	@Autowired
	public ShoppingItemsController(UserShoppingItemsService userShoppingItemsService, UserSessionInformation userinfo) {
		this.userShoppingItemsService = userShoppingItemsService;
		this.userinfo = userinfo;
	}

	@GetMapping
	public String getUserShoppingList(Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		Set<UserShoppingItemDto> shoplist = userShoppingItemsService.getShoppingList();

		model.addAttribute("item", new UserShoppingItemBindingModel());
		model.addAttribute("shoplist", shoplist);

		return "user_shoplist";
	}

	@PostMapping
	public String addShoppingItem(@Valid @ModelAttribute("item") UserShoppingItemBindingModel item, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "redirect:/user/shoplist";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		userShoppingItemsService.addShoppingItem(item);

		return "redirect:/user/shoplist";
	}

	@PostMapping("/{id}")
	public String deleteItem(@PathVariable Long id) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			userShoppingItemsService.deleteItem(id);
		} catch (RestClientException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";

	}

	@PostMapping("/clear")
	public String clearShoppingList() {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			userShoppingItemsService.clearShoppingList();

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";

	}
}
