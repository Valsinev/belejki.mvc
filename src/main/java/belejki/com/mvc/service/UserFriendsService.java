package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.FriendshipDto;

import java.util.List;

public interface UserFriendsService {

	List<FriendshipDto> getFriends();

	void addFriend(String friendEmail);

	void removeFriend(Long id);

	List<FriendshipDto> findAllByFirstName(String searchValue);
}
