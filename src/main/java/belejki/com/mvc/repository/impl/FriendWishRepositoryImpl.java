package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.repository.FriendWishRepository;
import belejki.com.mvc.util.PagedResponse;
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
	private final UserSessionInformation userSessionInformation;

	@Autowired
	public FriendWishRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userSessionInformation) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userSessionInformation = userSessionInformation;
	}


	@Override
	public List<WishDto> getFriendWishlistFilteredByPrice(Long maxPrice, String username, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		URI uri = UriComponentsBuilder
				.fromHttpUrl(appConfig.getBackendApiUrl() + "/user/wishlist/by-price-and-username")
				.queryParam("price", maxPrice)
				.queryParam("username", username)
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
	public List<WishDto> getFriendWishlistByFriendUsername(String username, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		String url = UriComponentsBuilder
				.fromHttpUrl(appConfig.getBackendApiUrl() + "/user/wishlist/user/" + username)
				.toUriString();


		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		return response.getBody().getContent();
	}

}
