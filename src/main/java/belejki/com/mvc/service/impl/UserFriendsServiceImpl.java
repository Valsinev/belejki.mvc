package belejki.com.mvc.service.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.service.UserFriendsService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserFriendsServiceImpl implements UserFriendsService {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final MessageSource messageSource;


	@Autowired
	public UserFriendsServiceImpl(AppConfig appConfig, RestTemplate restTemplate, MessageSource messageSource) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.messageSource = messageSource;
	}

	@Override
	public String getFriends(Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

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
		List<FriendshipDto> friends = response.getBody().getContent();

		model.addAttribute("friends", friends);

		return "user_friends";
	}

	@Override
	public String prepareFriedRecipesPage(String username, Model model, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";
		model.addAttribute("username", username);


		return "friend_recipes";
	}

	@Override
	public String getFriendRecipesByTitle(String searchValue, String username, Model model, HttpSession session, Locale locale) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
		List<RecipeDto> recipes = response.getBody().getContent();

		//convert links with locale prefix VIDEO: to be visualized as link Watch here
		checkForVideoLink(locale, recipes);


		model.addAttribute("recipes", recipes);
		model.addAttribute("username", username);


		return "friend_recipes";

	}

	@Override
	public String getFriendRecipesByIngredients(List<String> ingredients, String username, Model model, HttpSession session, Locale locale) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
		List<RecipeDto> recipes = response.getBody().getContent();

		checkForVideoLink(locale, recipes);

		model.addAttribute("recipes", recipes);
		model.addAttribute("username", username);

		System.out.println(recipes);


		return "friend_recipes";
	}

	@Override
	public String getFriendWishlistFilteredByPrice(Long maxPrice, String username, Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
		List<WishDto> wishlist = response.getBody().getContent();
		wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

		model.addAttribute("wishlist", wishlist);
		model.addAttribute("username", username);

		System.out.println(wishlist);


		return "friend_wishlist";
	}

	@Override
	public String prepareFriendWishlistPage(String username, Model model, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
		List<WishDto> wishlist = response.getBody().getContent();
		wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

		model.addAttribute("wishlist", wishlist);
		model.addAttribute("username", username);



		return "friend_wishlist";
	}

	@Override
	public String addFriend(String friendEmail, Model model, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) {
			return "redirect:/login";
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Assuming backend expects no body, friendEmail only in URL:
		HttpEntity<Void> request = new HttpEntity<>(headers);

		try {
			restTemplate.postForEntity(
					appConfig.getBackendApiUrl() + "/user/friendships/" + friendEmail,
					request,
					Void.class
			);
		} catch (HttpClientErrorException.Conflict ex) {  // HTTP 409 Conflict for duplicates
			model.addAttribute("errorMessage", "Failed to add friend, maybe is already in the friendlist or does not exist.");
			return "user_friends";  // Show friends page with error message
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			model.addAttribute("errorMessage", "Failed to add friend, maybe is already in the friendlist or does not exist.");
			return "user_friends";
		}

		return "redirect:/user/friends";
	}

	@Override
	public String removeFriend(Long id, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		try {

			restTemplate.exchange(
					appConfig.getBackendApiUrl() + "/user/friendships/" + id,
					HttpMethod.DELETE,
					request,
					Void.class
			);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/friends?error=delete";
		}
		return "redirect:/user/friends";
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
