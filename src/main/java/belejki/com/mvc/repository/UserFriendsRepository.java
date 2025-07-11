package belejki.com.mvc.repository;

import belejki.com.mvc.model.dto.FriendshipDto;

import java.util.List;

public interface UserFriendsRepository {

	List<FriendshipDto> findAll();

	void save(String friendEmail);

	void delete(Long id);

	List<FriendshipDto> findAllByFirstName(String searchValue);
}
