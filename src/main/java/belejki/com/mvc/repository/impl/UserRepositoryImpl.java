package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.dto.UserRegistrationDto;
import belejki.com.mvc.model.dto.UserSessionDto;
import belejki.com.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public UserRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	public UserSessionDto getCurrentUserInformation(String jwtToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<UserSessionDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/current",
				HttpMethod.GET,
				request,
				UserSessionDto.class
		);
		return response.getBody();
	}

	@Override
	public UserRegistrationDto save(UserRegistrationDto user) {
		// Send to REST API
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserRegistrationDto> request = new HttpEntity<>(user, headers);


		ResponseEntity<UserRegistrationDto> restResponse = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/users", request, UserRegistrationDto.class);

		return restResponse.getBody();
	}
}
