package belejki.com.mvc.shoppingItem.web;

import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.shoppingItem.web.binding.ShoppingItemBindingModel;
import belejki.com.mvc.shoppingItem.web.view.ShoppingItemViewModel;
import belejki.com.mvc.shoppingItem.service.ShoppingItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Set;

@Controller
@RequestMapping("/user/shoplist")
public class ShoppingItemsController {

	private final ShoppingItemService shoppingItemService;
	private final UserSessionInformation userinfo;

	@Autowired
	public ShoppingItemsController(ShoppingItemService shoppingItemService, UserSessionInformation userinfo) {
		this.shoppingItemService = shoppingItemService;
		this.userinfo = userinfo;
	}

	@GetMapping
	public String getUserShoppingList(Model model) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		Set<ShoppingItemViewModel> shoplist = shoppingItemService.getShoppingList();

		BigDecimal totalPrice = shoppingItemService.getTotalPrice();

		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("item", new ShoppingItemBindingModel());
		model.addAttribute("shoplist", shoplist);


		return "user_shoplist";
	}

	@PostMapping
	public String addShoppingItem(@Valid @ModelAttribute("item") ShoppingItemBindingModel item,
	                              BindingResult bindingResult,
	                              RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "redirect:/user/shoplist";
		}

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		shoppingItemService.addShoppingItem(item);

		return "redirect:/user/shoplist";
	}

	@PostMapping("/{id}")
	public String deleteItem(@PathVariable Long id) {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			shoppingItemService.deleteItem(id);
		} catch (RestClientException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";

	}

	@PostMapping("/clear")
	public String clearShoppingList() {

		if (userinfo.getJwtToken() == null) return "redirect:/login";

		try {
			shoppingItemService.clearShoppingList();

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";

	}
}
