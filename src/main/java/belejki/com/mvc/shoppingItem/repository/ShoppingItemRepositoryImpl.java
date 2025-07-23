package belejki.com.mvc.shoppingItem.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.shared.util.PagedResponse;
import belejki.com.mvc.shoppingItem.web.dto.ShoppingItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Repository
public class ShoppingItemRepositoryImpl implements ShoppingItemRepository {

	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userinfo;

	@Autowired
	public ShoppingItemRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userinfo) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userinfo = userinfo;
	}

	@Override
	public Set<ShoppingItemDto> getAll() {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<ShoppingItemDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<ShoppingItemDto>>() {
				}
		);
		return new HashSet<>(response.getBody().getContent());

	}

	@Override
	public ShoppingItemDto add(ShoppingItemDto item) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ShoppingItemDto> request = new HttpEntity<>(item, headers);

		ResponseEntity<ShoppingItemDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/shopping-list",
				request,
				ShoppingItemDto.class
		);

		return response.getBody();
	}


	@Override
	public BigDecimal getSumOfAllItemsPrice() {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<BigDecimal> request = new HttpEntity<>(headers);

		ResponseEntity<BigDecimal> exchange = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list/total",
				HttpMethod.GET,
				request,
				BigDecimal.class
		);

		return exchange.getBody();
	}



	@Override
	public void deleteById(Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);


		ResponseEntity<Void> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list/" + id,
				HttpMethod.DELETE,
				request,
				Void.class
		);
	}


	@Override
	public void deleteAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);


		restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list/empty",
				HttpMethod.DELETE,
				request,
				Void.class
		);
	}
}
