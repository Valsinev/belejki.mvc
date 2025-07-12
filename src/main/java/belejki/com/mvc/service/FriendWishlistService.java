package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.WishDto;
import belejki.com.mvc.model.view.WishViewModel;

import java.util.List;

public interface FriendWishlistService {

	List<WishViewModel> getFriendWishlistFilteredByPrice(Long maxPrice, String username);

	List<WishViewModel> prepareFriendWishlistPage(String username);
}
