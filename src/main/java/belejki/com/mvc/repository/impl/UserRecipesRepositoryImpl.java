package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.repository.UserRecipesRepository;
import belejki.com.mvc.util.PagedResponse;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Repository
public class UserRecipesRepositoryImpl implements UserRecipesRepository {

	private final RestTemplate restTemplate;
	private final AppConfig appConfig;

	public UserRecipesRepositoryImpl(RestTemplate restTemplate, AppConfig appConfig) {
		this.restTemplate = restTemplate;
		this.appConfig = appConfig;
	}

	@Override
	public void save(UserRecipeBindingModel recipe, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserRecipeBindingModel> request = new HttpEntity<>(recipe, headers);

		restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/recipes",
				request,
				Void.class);
	}

	@Override
	public List<UserRecipeBindingModel> searchByNameContaining(String searchValue, String jwtToken) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<UserRecipeBindingModel>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/recipes/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<UserRecipeBindingModel>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public List<UserRecipeBindingModel> searchByIngredients(List<String> ingredients, String jwtToken) {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		URI uri = UriComponentsBuilder
				.fromHttpUrl(appConfig.getBackendApiUrl() + "/user/recipes/by-ingredients")
				.queryParam("ingredients", ingredients.toArray())
				.build()
				.encode()
				.toUri();

		ResponseEntity<PagedResponse<UserRecipeBindingModel>> response = restTemplate.exchange(
				uri,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<UserRecipeBindingModel>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public void deleteById(Long id, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/recipes/" + id,
				HttpMethod.DELETE,
				request,
				Void.class
		);
	}
}
