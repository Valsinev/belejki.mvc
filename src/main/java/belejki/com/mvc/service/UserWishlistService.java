package belejki.com.mvc.service;

import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;

import java.util.List;

public interface UserWishlistService {
	List<WishDto> getWishlist(String jwtToken);

	WishDto createWish(UserWishBindingModel userWishBindingModel, String jwtToken);

	WishDto editWish(Long id, String jwtToken);

	WishDto updateWish(Long id, UserWishBindingModel userWishBindingModel, String jwtToken);

	WishDto deleteWish(Long id, String jwtToken);

	List<WishDto> searchByNameContaining(String searchValue, String jwtToken);

	List<WishDto> filterByPriceLessThan(Long maxPrice, String jwtToken);
}
