package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.WishDto;
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

	@Autowired
	public UserWishRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<WishDto> getAll(String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

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
	public WishDto save(WishDto wish, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<WishDto> request = new HttpEntity<>(wish, headers);

		ResponseEntity<WishDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/wishlist",
				request,
				WishDto.class);
		return response.getBody();
	}

	@Override
	public WishDto edit(Long id, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

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
	public WishDto update(Long id, WishDto wish, String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
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
	public WishDto deleteById(Long id, String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

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
	public List<WishDto> findAllByNameContaining(String searchValue, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

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
	public List<WishDto> findAllByPriceLessThan(Long maxPrice, String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

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
}
