package belejki.com.mvc.repository;

import belejki.com.mvc.model.dto.UserShoppingItemDto;
import java.util.Set;

public interface UserShoppingItemRepository {

	Set<UserShoppingItemDto> getAll();

	UserShoppingItemDto add(UserShoppingItemDto item);

	UserShoppingItemDto deleteById(Long id);

	void deleteAll();
}
