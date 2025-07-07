package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.ShoppingItemDto;
import belejki.com.mvc.service.UserShoppingItemsService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
        return userShoppingItemsService.getShoppingList(model, session);

    }

    @PostMapping
    public String addShoppingItem(@Valid @ModelAttribute("item") ShoppingItemDto item,
                                  Model model,
                                  HttpSession session) {
        return userShoppingItemsService.addShoppingItem(item, session);

    }

    @PostMapping("/{id}")
    public String deleteItem(@PathVariable Long id, HttpSession session) {
        return userShoppingItemsService.deleteItem(id, session);

    }

    @PostMapping("/clear")
    public String clearShoppingList(HttpSession session) {
        return userShoppingItemsService.clearShoppingList(session);

    }
}
