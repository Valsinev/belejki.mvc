package belejki.com.mvc.recipe.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.recipe.web.dto.RecipeDto;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.recipe.web.view.RecipeViewModel;
import belejki.com.mvc.shared.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<RecipeViewModel> getFriendRecipesByTitle(String searchValue, String username) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(userInformation.getJwtToken());

		Map<String, Object> body = new HashMap<>();
		body.put("friendUsername", username);
		body.put("recipeName", searchValue);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<PagedResponse<RecipeViewModel>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/friend/recipes",
				HttpMethod.POST,
				request,
				new ParameterizedTypeReference<PagedResponse<RecipeViewModel>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userInformation.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> body = new HashMap<>();
		body.put("ingredients", ingredients.toArray());
		body.put("friendUsername", username);

		HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

		ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/friend/recipes/by-ingredients-and-username",
				HttpMethod.POST,
				request,
				new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
				}
		);
		return response.getBody().getContent();
	}

}
