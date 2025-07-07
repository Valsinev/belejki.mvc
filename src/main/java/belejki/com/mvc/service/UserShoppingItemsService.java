package belejki.com.mvc.service;

import belejki.com.mvc.dto.ShoppingItemDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;

public interface UserShoppingItemsService {
	String getShoppingList(Model model, HttpSession session);

	String addShoppingItem(@Valid ShoppingItemDto item, HttpSession session);

	String deleteItem(Long id, HttpSession session);

	String clearShoppingList(HttpSession session);
}
