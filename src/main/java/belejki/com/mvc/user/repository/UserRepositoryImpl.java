package belejki.com.mvc.user.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.user.registration.web.dto.RegistrationDto;
import belejki.com.mvc.user.session.domain.UserSessionDto;
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
	public RegistrationDto save(RegistrationDto user) {
		// Send to REST API
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<RegistrationDto> request = new HttpEntity<>(user, headers);


		ResponseEntity<RegistrationDto> restResponse = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/users", request, RegistrationDto.class);

		return restResponse.getBody();
	}
}
