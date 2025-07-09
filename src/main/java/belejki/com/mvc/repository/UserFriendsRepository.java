package belejki.com.mvc.repository;

import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface UserFriendsRepository {
	List<FriendshipDto> getFriends(String token);

	List<UserRecipeBindingModel> getFriendRecipesByTitle(String searchValue, String username, String jwtToken);

	List<UserRecipeBindingModel> getFriendRecipesByIngredients(List<String> ingredients, String username, String jwtToken);

	List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String username, String jwtToken);

	List<WishDto> getFriendWishlistByFriendUsername(String username, String jwtToken);

	void addFriend(String friendEmail, String jwtToken);

	void removeFriend(Long id, String jwtToken);
}
