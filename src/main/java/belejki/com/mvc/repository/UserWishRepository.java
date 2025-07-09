package belejki.com.mvc.repository;

import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface UserWishRepository {
	List<WishDto> getAll(String jwtToken);

	WishDto save(WishDto wish, String jwtToken);

	WishDto edit(Long id, String jwtToken);

	WishDto update(Long id, WishDto wish, String jwtToken);

	WishDto deleteById(Long id, String jwtToken);

	List<WishDto> findAllByNameContaining(String searchValue, String jwtToken);

	List<WishDto> findAllByPriceLessThan(Long maxPrice, String jwtToken);
}
