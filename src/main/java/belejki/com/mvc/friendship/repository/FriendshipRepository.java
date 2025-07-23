package belejki.com.mvc.friendship.repository;

import belejki.com.mvc.friendship.web.binding.FriendshipBindingModel;
import belejki.com.mvc.friendship.web.view.FriendshipViewModel;

import java.util.List;

public interface FriendshipRepository {

	List<FriendshipViewModel> findAll();

	FriendshipViewModel save(FriendshipBindingModel friendshipBindingModel);

	List<FriendshipViewModel> findAllByFirstName(String searchValue);

	void delete(Long id);

}
