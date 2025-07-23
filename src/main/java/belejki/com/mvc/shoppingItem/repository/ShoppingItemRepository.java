package belejki.com.mvc.shoppingItem.repository;

import belejki.com.mvc.shoppingItem.web.dto.ShoppingItemDto;

import java.math.BigDecimal;
import java.util.Set;

public interface ShoppingItemRepository {

	Set<ShoppingItemDto> getAll();

	ShoppingItemDto add(ShoppingItemDto item);

	BigDecimal getSumOfAllItemsPrice();

	void deleteById(Long id);

	void deleteAll();
}
