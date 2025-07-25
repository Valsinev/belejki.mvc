package belejki.com.mvc.shoppingItem.service;

import belejki.com.mvc.shoppingItem.web.binding.ShoppingItemBindingModel;
import belejki.com.mvc.shoppingItem.web.view.ShoppingItemViewModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShoppingItemService {

	List<ShoppingItemViewModel> getShoppingList();

	ShoppingItemViewModel addShoppingItem(ShoppingItemBindingModel item);

	BigDecimal getTotalPrice();

	void deleteItem(Long id);

	void clearShoppingList();

}
