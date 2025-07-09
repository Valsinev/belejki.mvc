package belejki.com.mvc.service;

import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface FriendWishlistService {

	List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String username, String jwtToken);

	List<WishDto> prepareFriendWishlistPage(String username, String jwtToken);
}
