package belejki.com.mvc.repository;

import belejki.com.mvc.model.dto.FriendshipDto;

import java.util.List;

public interface UserFriendsRepository {

	List<FriendshipDto> findAll();

	FriendshipDto save(String friendEmail);

	FriendshipDto delete(Long id);

	List<FriendshipDto> findAllByFirstName(String searchValue);
}
