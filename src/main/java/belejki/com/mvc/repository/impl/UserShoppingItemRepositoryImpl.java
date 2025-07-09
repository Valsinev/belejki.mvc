package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.repository.UserShoppingItemRepository;
import belejki.com.mvc.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserShoppingItemRepositoryImpl implements UserShoppingItemRepository {

	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public UserShoppingItemRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public Set<UserShoppingItemDto> getAll(String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<UserShoppingItemDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<UserShoppingItemDto>>() {
				}
		);
		return new HashSet<>(response.getBody().getContent());

	}

	@Override
	public UserShoppingItemDto add(UserShoppingItemDto item, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserShoppingItemDto> request = new HttpEntity<>(item, headers);

		ResponseEntity<UserShoppingItemDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/shopping-list",
				request,
				UserShoppingItemDto.class
		);

		return response.getBody();
	}

	@Override
	public UserShoppingItemDto deleteById(Long id, String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<UserShoppingItemDto> request = new HttpEntity<>(headers);


		ResponseEntity<UserShoppingItemDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list/" + id,
				HttpMethod.DELETE,
				request,
				UserShoppingItemDto.class
		);
		return response.getBody();
	}

	@Override
	public void deleteAll(String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);


		restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/shopping-list/empty",
				HttpMethod.DELETE,
				request,
				Void.class
		);
	}
}
