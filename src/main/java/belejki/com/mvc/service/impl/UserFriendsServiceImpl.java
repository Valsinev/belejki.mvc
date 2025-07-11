package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.FriendshipDto;
import belejki.com.mvc.repository.UserFriendsRepository;
import belejki.com.mvc.service.UserFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {


	private final UserFriendsRepository userFriendsRepository;

	@Autowired
	public UserFriendsServiceImpl(UserFriendsRepository userFriendsRepository) {
		this.userFriendsRepository = userFriendsRepository;
	}

	public List<FriendshipDto> getFriends() {

		return userFriendsRepository.findAll();

	}


	@Override
	public void addFriend(String friendEmail) {

		userFriendsRepository.save(friendEmail);

	}

	@Override
	public List<FriendshipDto> findAllByFirstName(String searchValue) {
		return userFriendsRepository.findAllByFirstName(searchValue);
	}

	@Override
	public void removeFriend(Long id) {

		userFriendsRepository.delete(id);

	}


}
