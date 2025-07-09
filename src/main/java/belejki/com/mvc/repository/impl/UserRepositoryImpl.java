package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.UserRegistrationDto;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import belejki.com.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
