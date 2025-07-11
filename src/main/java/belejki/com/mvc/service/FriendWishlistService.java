package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.WishDto;

import java.util.List;

public interface FriendWishlistService {

	List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String username);

	List<WishDto> prepareFriendWishlistPage(String username);
}
