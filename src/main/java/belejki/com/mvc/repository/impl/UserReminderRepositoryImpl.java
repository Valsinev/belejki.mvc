package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.dto.UserReminderDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.repository.UserReminderRepository;
import belejki.com.mvc.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class UserReminderRepositoryImpl implements UserReminderRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userSessionInformation;

	@Autowired
	public UserReminderRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userSessionInformation) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userSessionInformation = userSessionInformation;
	}

	@Override
	public List<UserReminderDto> findAll() throws RestClientException {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<UserReminderDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<UserReminderDto>>() {
				}
		);

		return response.getBody().getContent();
	}

	@Override
	public UserReminderDto save(UserReminderDto reminder) throws RestClientException {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserReminderDto> request = new HttpEntity<>(reminder, headers);

		ResponseEntity<UserReminderDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/reminders",
				request,
				UserReminderDto.class);

		return response.getBody();
	}

	@Override
	public UserReminderDto edit(Long id) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<UserReminderDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/id/" + id,
				HttpMethod.GET,
				request,
				UserReminderDto.class
		);

		return response.getBody();
	}

	@Override
	public UserReminderDto update(Long id, UserReminderDto reminder) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserReminderDto> request = new HttpEntity<>(reminder, headers);
		ResponseEntity<UserReminderDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/" + id,
				HttpMethod.PUT,
				request,
				UserReminderDto.class
		);
		return response.getBody();
	}

	@Override
	public UserReminderDto deleteById(Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<UserReminderDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/id/" + id,
				HttpMethod.DELETE,
				request,
				UserReminderDto.class
		);
		return response.getBody();
	}

	@Override
	public List<UserReminderDto> searchByNameContaining(String searchValue) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<UserReminderDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/name/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<UserReminderDto>>() {
				}
		);
		return response.getBody().getContent();
	}
}
