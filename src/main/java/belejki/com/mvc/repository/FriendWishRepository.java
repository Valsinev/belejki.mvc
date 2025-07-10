package belejki.com.mvc.repository;

import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface FriendWishRepository {
	List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String username);

	List<WishDto> getFriendWishlistByFriendUsername(String username);
}
