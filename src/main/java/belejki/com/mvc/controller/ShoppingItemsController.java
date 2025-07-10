package belejki.com.mvc.controller;

import belejki.com.mvc.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.service.UserShoppingItemsService;
import jakarta.servlet.http.HttpSession;
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

	@Autowired
	public ShoppingItemsController(UserShoppingItemsService userShoppingItemsService) {
		this.userShoppingItemsService = userShoppingItemsService;
	}

	@GetMapping
	public String getUserShoppingList(Model model, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		Set<UserShoppingItemDto> shoplist = userShoppingItemsService.getShoppingList(token);

		model.addAttribute("item", new UserShoppingItemBindingModel());
		model.addAttribute("shoplist", shoplist);

		return "user_shoplist";
	}

	@PostMapping
	public String addShoppingItem(@Valid @ModelAttribute("item") UserShoppingItemBindingModel item,
	                              HttpSession session,
	                              BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "redirect:/user/shoplist";
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		userShoppingItemsService.addShoppingItem(item, token);

		return "redirect:/user/shoplist";
	}

	@PostMapping("/{id}")
	public String deleteItem(@PathVariable Long id, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			userShoppingItemsService.deleteItem(id, token);
		} catch (RestClientException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";

	}

	@PostMapping("/clear")
	public String clearShoppingList(HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		try {
			userShoppingItemsService.clearShoppingList(token);

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";

	}
}
