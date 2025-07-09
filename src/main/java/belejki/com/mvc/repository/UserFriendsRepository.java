package belejki.com.mvc.repository;

import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface UserFriendsRepository {
	List<FriendshipDto> getFriends(String token);

	void addFriend(String friendEmail, String jwtToken);

	void removeFriend(Long id, String jwtToken);
}
