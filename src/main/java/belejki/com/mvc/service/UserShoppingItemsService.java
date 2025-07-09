package belejki.com.mvc.service;

import belejki.com.mvc.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import java.util.Set;

public interface UserShoppingItemsService {
	Set<UserShoppingItemDto> getShoppingList(String jwtToken);

	UserShoppingItemDto addShoppingItem(UserShoppingItemBindingModel item, String jwtToken);

	UserShoppingItemDto deleteItem(Long id, String jwtToken);

	void clearShoppingList(String jwtToken);
}
