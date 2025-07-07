package belejki.com.mvc.service.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.service.UserWishlistService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Service
public class UserWishlistServiceImpl implements UserWishlistService {

	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public UserWishlistServiceImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public String getWishlist(Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		List<WishDto> wishlist = response.getBody().getContent();
		wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

		model.addAttribute("wishlist", wishlist);

		return "user_wishlist";
	}

	@Override
	public String createWish(WishDto wish, BindingResult result, HttpSession session, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editing", false);
			return "create_wish";
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<WishDto> request = new HttpEntity<>(wish, headers);

		restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/wishlist",
				request,
				Void.class);

		return "redirect:/user/wishes";
	}

	@Override
	public String editWish(Long id, HttpSession session, Model model) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<WishDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/" + id,
				HttpMethod.GET,
				request,
				WishDto.class
		);

		WishDto wish = response.getBody();

		model.addAttribute("wish", wish);
		model.addAttribute("editing", true);

		return "create_wish";
	}

	@Override
	public String updateWish(Long id, WishDto wish, BindingResult result, HttpSession session, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("editing", true);
			return "create_wish";
		}

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<WishDto> request = new HttpEntity<>(wish, headers);

		restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/update/" + id,
				HttpMethod.PUT,
				request,
				Void.class
		);


		return "redirect:/user/wishes";
	}

	@Override
	public String deleteWish(Long id, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		try {

			restTemplate.exchange(
					appConfig.getBackendApiUrl() + "/user/wishlist/" + id,
					HttpMethod.DELETE,
					request,
					Void.class
			);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/wishes?error=delete";
		}
		return "redirect:/user/wishes";
	}

	@Override
	public String searchByNameContaining(String searchValue, Model model, HttpSession session) {

		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/description/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		List<WishDto> wishlist = response.getBody().getContent();
		wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

		model.addAttribute("wishlist", wishlist);


		return "user_wishlist";
	}

	@Override
	public String filterByPriceLessThan(Long maxPrice, Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");

		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/price/" + maxPrice,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		List<WishDto> wishlist = response.getBody().getContent();
		wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

		model.addAttribute("wishlist", wishlist);


		return "user_wishlist";
	}
}
