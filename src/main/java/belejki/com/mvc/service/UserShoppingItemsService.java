package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import java.util.Set;

public interface UserShoppingItemsService {

	Set<UserShoppingItemDto> getShoppingList();

	UserShoppingItemDto addShoppingItem(UserShoppingItemBindingModel item);

	UserShoppingItemDto deleteItem(Long id);

	void clearShoppingList();
}
