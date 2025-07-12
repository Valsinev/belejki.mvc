package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.FriendshipDto;
import belejki.com.mvc.model.view.FriendshipViewModel;

import java.util.List;

public interface UserFriendsService {

	List<FriendshipViewModel> getFriends();

	FriendshipViewModel addFriend(String friendEmail);

	FriendshipViewModel removeFriend(Long id);

	List<FriendshipViewModel> findAllByFirstName(String searchValue);
}
