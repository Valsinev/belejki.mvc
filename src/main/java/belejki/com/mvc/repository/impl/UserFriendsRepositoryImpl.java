package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.repository.UserFriendsRepository;
import belejki.com.mvc.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Repository
public class UserFriendsRepositoryImpl implements UserFriendsRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public UserFriendsRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	/***
	 * gets all the friends of the user
	 * @param token
	 * @return
	 */
	@Override
	public List<FriendshipDto> getFriends(String token) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<FriendshipDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/friendships",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<FriendshipDto>>() {
				}
		);

		return response.getBody().getContent();
	}

	@Override
	public void addFriend(String friendEmail, String jwtToken) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Assuming backend expects no body, friendEmail only in URL:
		HttpEntity<Void> request = new HttpEntity<>(headers);

			restTemplate.postForEntity(
					appConfig.getBackendApiUrl() + "/user/friendships/" + friendEmail,
					request,
					Void.class
			);
	}

	@Override
	public void removeFriend(Long id, String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

			restTemplate.exchange(
					appConfig.getBackendApiUrl() + "/user/friendships/" + id,
					HttpMethod.DELETE,
					request,
					Void.class
			);
	}
}
