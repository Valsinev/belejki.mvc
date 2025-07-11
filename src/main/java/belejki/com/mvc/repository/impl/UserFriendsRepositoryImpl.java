package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.dto.FriendshipDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.repository.UserFriendsRepository;
import belejki.com.mvc.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class UserFriendsRepositoryImpl implements UserFriendsRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userinfo;

	@Autowired
	public UserFriendsRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userinfo) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userinfo = userinfo;
	}

	@Override
	public List<FriendshipDto> findAll() {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

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
	public void save(String friendEmail) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());
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
	public List<FriendshipDto> findAllByFirstName(String searchValue) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<FriendshipDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/friendships/first-name/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<FriendshipDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public void delete(Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

			restTemplate.exchange(
					appConfig.getBackendApiUrl() + "/user/friendships/" + id,
					HttpMethod.DELETE,
					request,
					Void.class
			);
	}

}
