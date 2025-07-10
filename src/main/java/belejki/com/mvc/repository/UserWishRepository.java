package belejki.com.mvc.repository;

import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface UserWishRepository {
	List<WishDto> findAll();

	WishDto save(WishDto wish);

	WishDto edit(Long id);

	WishDto update(Long id, WishDto wish);

	WishDto deleteById(Long id);

	List<WishDto> findAllByNameContaining(String searchValue);

	List<WishDto> findAllByPriceLessThan(Long maxPrice);
}
