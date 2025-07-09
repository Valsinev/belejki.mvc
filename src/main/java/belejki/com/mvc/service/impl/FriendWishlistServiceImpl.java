package belejki.com.mvc.service.impl;

import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.repository.FriendWishRepository;
import belejki.com.mvc.service.FriendWishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendWishlistServiceImpl implements FriendWishlistService {

	private final FriendWishRepository friendWishRepository;

	@Autowired
	public FriendWishlistServiceImpl(FriendWishRepository friendWishRepository) {
		this.friendWishRepository = friendWishRepository;
	}

	@Override
	public List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String username, String jwtToken) {

		List<WishDto> wishlist = friendWishRepository.getFriendWishlistFilteredByPrice(maxPrice, username, jwtToken);

		return wishlist;
	}

	@Override
	public List<WishDto> prepareFriendWishlistPage(String username, String jwtToken) {

		List<WishDto> wishlist = friendWishRepository.getFriendWishlistByFriendUsername(username, jwtToken);

		return wishlist;
	}
}
