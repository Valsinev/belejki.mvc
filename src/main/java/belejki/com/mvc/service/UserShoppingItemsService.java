package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.model.view.ShoppingItemViewModel;

import java.util.Set;

public interface UserShoppingItemsService {

	Set<ShoppingItemViewModel> getShoppingList();

	ShoppingItemViewModel addShoppingItem(UserShoppingItemBindingModel item);

	ShoppingItemViewModel deleteItem(Long id);

	void clearShoppingList();
}
