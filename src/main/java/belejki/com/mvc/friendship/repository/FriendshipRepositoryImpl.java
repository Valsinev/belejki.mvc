package belejki.com.mvc.friendship.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.friendship.web.binding.FriendshipBindingModel;
import belejki.com.mvc.friendship.web.view.FriendshipViewModel;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.shared.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class FriendshipRepositoryImpl implements FriendshipRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userinfo;

	@Autowired
	public FriendshipRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userinfo) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userinfo = userinfo;
	}

	@Override
	public List<FriendshipViewModel> findAll() {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<FriendshipViewModel>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/friendships",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<FriendshipViewModel>>() {
				}
		);

		return response.getBody().getContent();
	}

	@Override
	public FriendshipViewModel save(FriendshipBindingModel friendshipBindingModel) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Assuming backend expects no body, friendEmail only in URL:
		HttpEntity<FriendshipBindingModel> request = new HttpEntity<>(friendshipBindingModel, headers);

		ResponseEntity<FriendshipViewModel> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/friendships",
				request,
				FriendshipViewModel.class
		);

		return response.getBody();
	}

	@Override
	public List<FriendshipViewModel> findAllByFirstName(String searchValue) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<FriendshipViewModel>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/friendships/first-name/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<FriendshipViewModel>>() {
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
