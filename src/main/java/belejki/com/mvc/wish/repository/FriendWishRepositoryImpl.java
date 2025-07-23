package belejki.com.mvc.wish.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.friendship.web.binding.FriendshipBindingModel;
import belejki.com.mvc.friendship.web.view.FriendshipViewModel;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.shared.util.PagedResponse;
import belejki.com.mvc.wish.web.dto.WishDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Repository
public class FriendWishRepositoryImpl implements FriendWishRepository {

	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userInformation;

	@Autowired
	public FriendWishRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userInformation) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userInformation = userInformation;
	}


	@Override
	public List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String friendUsername) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		URI uri = UriComponentsBuilder
				.fromHttpUrl(appConfig.getBackendApiUrl() + "/user/wishlist/by-price-and-username")
				.queryParam("price", maxPrice)
				.queryParam("username", friendUsername)
				.build()
				.encode()
				.toUri();

		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				uri,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public List<WishDto> getFriendWishlistByFriendUsername(String friendUsername) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInformation.getJwtToken());

		HttpEntity<FriendshipViewModel> request = new HttpEntity<>(headers);


		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/friend/wishlist/" + friendUsername,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		return response.getBody().getContent();
	}

}
