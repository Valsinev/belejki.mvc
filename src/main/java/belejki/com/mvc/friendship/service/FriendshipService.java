package belejki.com.mvc.friendship.service;

import belejki.com.mvc.friendship.web.binding.FriendshipBindingModel;
import belejki.com.mvc.friendship.web.view.FriendshipViewModel;

import java.util.List;

public interface FriendshipService {

	List<FriendshipViewModel> getFriends();

	FriendshipViewModel addFriend(FriendshipBindingModel friendshipBindingModel);

	List<FriendshipViewModel> findAllByFirstName(String searchValue);

	void removeFriend(Long id);
}
