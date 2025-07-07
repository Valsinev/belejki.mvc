package belejki.com.mvc.service.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.exceptions.UnauthorizedException;
import belejki.com.mvc.service.UserRecipesService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserRecipesServiceImpl implements UserRecipesService {

	private final RestTemplate restTemplate;
	private final AppConfig appConfig;
	private final MessageSource messageSource;

	@Autowired
	public UserRecipesServiceImpl(RestTemplate restTemplate, AppConfig appConfig, MessageSource messageSource) {
		this.restTemplate = restTemplate;
		this.appConfig = appConfig;
		this.messageSource = messageSource;
	}

	@Override
	public void createRecipe(RecipeDto recipe, BindingResult bindingResult, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) throw new UnauthorizedException("User authentication failed.");

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RecipeDto> request = new HttpEntity<>(recipe, headers);

		restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/recipes",
				request,
				Void.class);
	}

	@Override
	public String getRecipes(HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		return "user_recipes";
	}

	@Override
	public String searchByNameContaining(String searchValue, Model model, HttpSession session, Locale locale) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/recipes/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
				}
		);
		List<RecipeDto> recipes = response.getBody().getContent();

		checkForVideoLink(locale, recipes);

		model.addAttribute("recipes", recipes);


		return "user_recipes";
	}

	@Override
	public String searchByIngredients(List<String> ingredients, Model model, HttpSession session, Locale locale) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
		List<RecipeDto> recipes = response.getBody().getContent();

		checkForVideoLink(locale, recipes);

		model.addAttribute("recipes", recipes);


		return "user_recipes";
	}

	@Override
	public String deleteRecipeById(Long id, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		try {
			restTemplate.exchange(
					appConfig.getBackendApiUrl() + "/user/recipes/" + id,
					HttpMethod.DELETE,
					request,
					Void.class
			);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/reminders?error=delete";
		}
		return "redirect:/user/recipes";
	}

	private void checkForVideoLink(Locale locale, List<RecipeDto> recipes) {
		String videoPrefix = messageSource.getMessage("video.prefix", null, locale);
		String linkText = messageSource.getMessage("video.watch.here", null, locale);

		String regex = "(?i)[\\r\\n\\s]*" + Pattern.quote(videoPrefix) + ":\\s*(https?://\\S+)";
		String replacement = "<strong>" + videoPrefix + ":</strong> <a href=\"$1\" target=\"_blank\">" + linkText + "</a>";

		Pattern pattern = Pattern.compile(regex);

		recipes.forEach(recipeDto -> {
			Matcher matcher = pattern.matcher(recipeDto.getHowToMake());
			String result = matcher.replaceAll(replacement);
			recipeDto.setHowToMake(result);
		});
	}
}
