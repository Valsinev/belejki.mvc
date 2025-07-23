package belejki.com.mvc.wish.repository;

import belejki.com.mvc.wish.web.dto.WishDto;

import java.util.List;

public interface FriendWishRepository {
	List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String friendUsername);

	List<WishDto> getFriendWishlistByFriendUsername(String friendUsername);
}
