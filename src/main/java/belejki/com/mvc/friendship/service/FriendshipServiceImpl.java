package belejki.com.mvc.friendship.service;

import belejki.com.mvc.friendship.web.view.FriendshipViewModel;
import belejki.com.mvc.friendship.repository.FriendshipRepository;
import belejki.com.mvc.friendship.web.binding.FriendshipBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {


	private final FriendshipRepository friendshipRepository;

	@Autowired
	public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
		this.friendshipRepository = friendshipRepository;
	}

	public List<FriendshipViewModel> getFriends() {

		return friendshipRepository.findAll();

	}


	@Override
	public FriendshipViewModel addFriend(FriendshipBindingModel friendshipBindingModel) {

		return friendshipRepository.save(friendshipBindingModel);

	}

	@Override
	public List<FriendshipViewModel> findAllByFirstName(String searchValue) {

		return friendshipRepository.findAllByFirstName(searchValue);
	}

	@Override
	public void removeFriend(Long id) {

		friendshipRepository.delete(id);

	}


}
