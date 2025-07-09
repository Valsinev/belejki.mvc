package belejki.com.mvc.repository;

import belejki.com.mvc.dto.UserShoppingItemDto;
import java.util.Set;

public interface UserShoppingItemRepository {
	Set<UserShoppingItemDto> getAll(String jwtToken);

	UserShoppingItemDto add(UserShoppingItemDto item, String jwtToken);

	UserShoppingItemDto deleteById(Long id, String jwtToken);

	void deleteAll(String jwtToken);
}
