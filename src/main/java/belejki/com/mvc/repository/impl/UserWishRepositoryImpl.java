package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.dto.WishDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.repository.UserWishRepository;
import belejki.com.mvc.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class UserWishRepositoryImpl implements UserWishRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userinfo;

	@Autowired
	public UserWishRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userinfo) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userinfo = userinfo;
	}

	@Override
	public List<WishDto> findAll() {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public WishDto save(WishDto wish) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<WishDto> request = new HttpEntity<>(wish, headers);

		ResponseEntity<WishDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/wishlist",
				request,
				WishDto.class);
		return response.getBody();
	}

	@Override
	public WishDto edit(Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<WishDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/" + id,
				HttpMethod.GET,
				request,
				WishDto.class
		);

		return response.getBody();
	}

	@Override
	public WishDto update(Long id, WishDto wish) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<WishDto> request = new HttpEntity<>(wish, headers);

		ResponseEntity<WishDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/update/" + id,
				HttpMethod.PUT,
				request,
				WishDto.class
		);

		return response.getBody();
	}

	@Override
	public WishDto deleteById(Long id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<WishDto> request = new HttpEntity<>(headers);


		ResponseEntity<WishDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/" + id,
				HttpMethod.DELETE,
				request,
				WishDto.class
		);

		return response.getBody();
	}

	@Override
	public List<WishDto> findAllByNameContaining(String searchValue) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/description/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public List<WishDto> findAllByPriceLessThan(Long maxPrice) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/price/" + maxPrice,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<WishDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public WishDto findById(Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userinfo.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<WishDto> request = new HttpEntity<>(headers);

		ResponseEntity<WishDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/wishlist/" + id,
				HttpMethod.GET,
				request,
				WishDto.class);
		return response.getBody();
	}
}
