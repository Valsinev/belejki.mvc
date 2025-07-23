package belejki.com.mvc.recipe.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.recipe.web.dto.RecipeDto;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.shared.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Repository
public class RecipeRepositoryImpl implements RecipeRepository {

	private final RestTemplate restTemplate;
	private final AppConfig appConfig;
	private final UserSessionInformation userInfo;

	@Autowired
	public RecipeRepositoryImpl(RestTemplate restTemplate, AppConfig appConfig, UserSessionInformation userInfo) {
		this.restTemplate = restTemplate;
		this.appConfig = appConfig;
		this.userInfo = userInfo;
	}

	@Override
	public RecipeDto save(RecipeDto recipe) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInfo.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RecipeDto> request = new HttpEntity<>(recipe, headers);

		ResponseEntity<RecipeDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/recipes",
				request,
				RecipeDto.class);

		return response.getBody();
	}

	@Override
	public List<RecipeDto> findAllByNameContaining(String searchValue) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/recipes/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public List<RecipeDto> findAllByIngredients(List<String> ingredients) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		URI uri = UriComponentsBuilder
				.fromHttpUrl(appConfig.getBackendApiUrl() + "/user/recipes/by-ingredients")
				.queryParam("ingredients", ingredients.toArray())
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

	@Override
	public void deleteById(Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/recipes/" + id,
				HttpMethod.DELETE,
				request,
				Void.class
		);
	}
}
