package belejki.com.mvc.service.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.ShoppingItemDto;
import belejki.com.mvc.service.UserShoppingItemsService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserShoppingItemsServiceImpl implements UserShoppingItemsService {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public UserShoppingItemsServiceImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public String getShoppingList(Model model, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<ShoppingItemDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<ShoppingItemDto>>() {
				}
		);
		List<ShoppingItemDto> shoplist = response.getBody().getContent();

		model.addAttribute("item", new ShoppingItemDto());
		model.addAttribute("shoplist", shoplist);

		return "user_shoplist";
	}

	@Override
	public String addShoppingItem(ShoppingItemDto item, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/user/dashboard";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ShoppingItemDto> request = new HttpEntity<>(item, headers);

		restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/shopping-list",
				request,
				Void.class
		);

		return "redirect:/user/shoplist";
	}

	@Override
	public String deleteItem(Long id, HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		try {

			restTemplate.exchange(
					appConfig.getBackendApiUrl() + "/user/shopping-list/" + id,
					HttpMethod.DELETE,
					request,
					Void.class
			);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";
	}

	@Override
	public String clearShoppingList(HttpSession session) {
		String token = (String) session.getAttribute("jwt");
		if (token == null) return "redirect:/login";

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		try {

			restTemplate.exchange(
					appConfig.getBackendApiUrl() + "/user/shopping-list/empty",
					HttpMethod.DELETE,
					request,
					Void.class
			);
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			return "redirect:/user/shoplist?error=delete";
		}
		return "redirect:/user/shoplist";
	}
}
