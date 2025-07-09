package belejki.com.mvc.service.impl;

import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.repository.UserFriendsRepository;
import belejki.com.mvc.service.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {


	private final UserFriendsRepository userFriendsRepository;

	@Autowired
	public UserFriendsServiceImpl(UserFriendsRepository userFriendsRepository) {
		this.userFriendsRepository = userFriendsRepository;
	}

	public List<FriendshipDto> getFriends(String token) {

		return userFriendsRepository.getFriends(token);

	}

	@Override
	public List<UserRecipeBindingModel> getFriendRecipesByTitle(String searchValue, String username, String jwtToken) {

		return userFriendsRepository.getFriendRecipesByTitle(searchValue, username, jwtToken);

	}

	@Override
	public List<UserRecipeBindingModel> getFriendRecipesByIngredients(List<String> ingredients, String username, String jwtToken) {

		return userFriendsRepository.getFriendRecipesByIngredients(ingredients, username, jwtToken);

	}

	@Override
	public List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String username, String jwtToken) {

		List<WishDto> wishlist = userFriendsRepository.getFriendWishlistFilteredByPrice(maxPrice, username, jwtToken);
		wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

		return wishlist;
	}

	@Override
	public List<WishDto> prepareFriendWishlistPage(String username, String jwtToken) {

		List<WishDto> wishlist = userFriendsRepository.getFriendWishlistByFriendUsername(username, jwtToken);

		wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

		return wishlist;
	}

	@Override
	public void addFriend(String friendEmail, String jwtToken) {

		userFriendsRepository.addFriend(friendEmail, jwtToken);

	}

	@Override
	public void removeFriend(Long id,  String jwtToken) {

		userFriendsRepository.removeFriend(id, jwtToken);

	}

}
