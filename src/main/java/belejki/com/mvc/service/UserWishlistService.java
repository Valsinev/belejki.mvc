package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;
import belejki.com.mvc.model.view.WishViewModel;

import java.util.List;

public interface UserWishlistService {

	List<WishViewModel> getWishlist();

	WishViewModel createWish(UserWishBindingModel userWishBindingModel);

	WishViewModel editWish(Long id);

	WishViewModel updateWish(Long id, UserWishBindingModel userWishBindingModel);

	WishViewModel deleteWish(Long id);

	List<WishViewModel> searchByNameContaining(String searchValue);

	List<WishViewModel> filterByPriceLessThan(Long maxPrice);

	WishViewModel findById(Long id);
}
