package belejki.com.mvc.service;

import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;

import java.util.List;

public interface UserWishlistService {

	List<WishDto> getWishlist();

	WishDto createWish(UserWishBindingModel userWishBindingModel);

	WishDto editWish(Long id);

	WishDto updateWish(Long id, UserWishBindingModel userWishBindingModel);

	WishDto deleteWish(Long id);

	List<WishDto> searchByNameContaining(String searchValue);

	List<WishDto> filterByPriceLessThan(Long maxPrice);
}
