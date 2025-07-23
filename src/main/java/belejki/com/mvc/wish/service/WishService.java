package belejki.com.mvc.wish.service;

import belejki.com.mvc.wish.web.binding.WishBindingModel;
import belejki.com.mvc.wish.web.view.WishViewModel;

import java.util.List;

public interface WishService {

	List<WishViewModel> getWishlist();

	WishViewModel createWish(WishBindingModel wishBindingModel);

	WishViewModel editWish(Long id);

	WishViewModel updateWish(WishBindingModel wishBindingModel);

	List<WishViewModel> searchByNameContaining(String searchValue);

	List<WishViewModel> filterByPriceLessThan(Long maxPrice);

	WishViewModel findById(Long id);

	void deleteWish(Long id);

}
