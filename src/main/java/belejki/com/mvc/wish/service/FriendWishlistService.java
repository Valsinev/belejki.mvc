package belejki.com.mvc.wish.service;

import belejki.com.mvc.wish.web.view.WishViewModel;

import java.util.List;

public interface FriendWishlistService {

	List<WishViewModel> getFriendWishlistFilteredByPrice(Long maxPrice, String friendUsername);

	List<WishViewModel> findFriendWishlist(String friendUsername);
}
