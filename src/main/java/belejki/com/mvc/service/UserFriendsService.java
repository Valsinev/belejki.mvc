package belejki.com.mvc.service;

import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface UserFriendsService {
	List<FriendshipDto> getFriends(String jwtToken);

	void addFriend(String friendEmail, String jwtToken);

	void removeFriend(Long id, String jwtToken);
}
