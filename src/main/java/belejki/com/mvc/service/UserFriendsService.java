package belejki.com.mvc.service;

import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.dto.WishDto;

import java.util.List;

public interface UserFriendsService {

	List<FriendshipDto> getFriends();

	void addFriend(String friendEmail);

	void removeFriend(Long id);

	List<FriendshipDto> findAllByFirstName(String searchValue);
}
