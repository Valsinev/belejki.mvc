package belejki.com.mvc.friendship.web.view;

import lombok.Data;

@Data
public class FriendshipViewModel {

	private Long id;
	private Long friendId;
	private String friendUsername;
	private String friendFirstName;
	private String friendLastName;
}
