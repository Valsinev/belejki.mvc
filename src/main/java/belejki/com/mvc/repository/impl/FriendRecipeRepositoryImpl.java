package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.repository.FriendRecipeRepository;
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
public class FriendRecipeRepositoryImpl implements FriendRecipeRepository {

	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userInformation;

	@Autowired
	public FriendRecipeRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userInformation) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userInformation = userInformation;
	}

	@Override
	public List<RecipeDto> getFriendRecipesByTitle(String searchValue, String username) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		String url = UriComponentsBuilder
				.fromHttpUrl(appConfig.getBackendApiUrl() + "/user/recipes/by-name-and-username")
				.queryParam("username", username)
				.queryParam("recipeName", searchValue)
				.toUriString();


		ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
				url,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		URI uri = UriComponentsBuilder
				.fromHttpUrl(appConfig.getBackendApiUrl() + "/user/recipes/by-ingredients-and-username")
				.queryParam("ingredients", ingredients.toArray())
				.queryParam("username", username)
				.build()
				.encode()
				.toUri();

		ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
				uri,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
				}
		);
		return response.getBody().getContent();
	}

}
