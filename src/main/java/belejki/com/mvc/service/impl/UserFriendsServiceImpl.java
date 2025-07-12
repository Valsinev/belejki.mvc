package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.FriendshipDto;
import belejki.com.mvc.model.view.FriendshipViewModel;
import belejki.com.mvc.repository.UserFriendsRepository;
import belejki.com.mvc.service.UserFriendsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {


	private final UserFriendsRepository userFriendsRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserFriendsServiceImpl(UserFriendsRepository userFriendsRepository, ModelMapper modelMapper) {
		this.userFriendsRepository = userFriendsRepository;
		this.modelMapper = modelMapper;
	}

	public List<FriendshipViewModel> getFriends() {

		List<FriendshipDto> all = userFriendsRepository.findAll();

		return all.stream()
				.map(friendshipDto -> modelMapper.map(friendshipDto, FriendshipViewModel.class))
				.toList();
	}


	@Override
	public FriendshipViewModel addFriend(String friendEmail) {

		FriendshipDto saved = userFriendsRepository.save(friendEmail);

		return modelMapper.map(saved, FriendshipViewModel.class);
	}

	@Override
	public List<FriendshipViewModel> findAllByFirstName(String searchValue) {

		List<FriendshipDto> allByFirstName = userFriendsRepository.findAllByFirstName(searchValue);

		return allByFirstName.stream()
				.map(friendshipDto -> modelMapper.map(friendshipDto, FriendshipViewModel.class))
				.toList();
	}

	@Override
	public FriendshipViewModel removeFriend(Long id) {

		FriendshipDto deleted = userFriendsRepository.delete(id);

		return modelMapper.map(deleted, FriendshipViewModel.class);
	}


}
