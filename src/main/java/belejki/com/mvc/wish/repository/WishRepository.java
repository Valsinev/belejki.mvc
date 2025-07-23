package belejki.com.mvc.wish.repository;

import belejki.com.mvc.wish.web.dto.WishDto;

import java.util.List;

public interface WishRepository {
	List<WishDto> findAll();

	WishDto save(WishDto wish);

	WishDto edit(Long id);

	WishDto update(WishDto wish);

	List<WishDto> findAllByNameContaining(String searchValue);

	List<WishDto> findAllByPriceLessThan(Long maxPrice);

	WishDto findById(Long id);

	void deleteById(Long id);

}
