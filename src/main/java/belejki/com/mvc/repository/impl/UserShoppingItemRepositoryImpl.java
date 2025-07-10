package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.UserShoppingItemDto;
import belejki.com.mvc.model.binding.UserShoppingItemBindingModel;
import belejki.com.mvc.model.session.UserSessionInformation;
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
	private final UserSessionInformation userinfo;

	@Autowired
	public UserShoppingItemRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userinfo) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userinfo = userinfo;
	}

	@Override
	public Set<UserShoppingItemDto> getAll() {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

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
	public UserShoppingItemDto add(UserShoppingItemDto item) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());
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
	public UserShoppingItemDto deleteById(Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

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
