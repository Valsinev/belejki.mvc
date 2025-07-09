package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.UserReminderDto;
import belejki.com.mvc.model.binding.UserReminderBindingModel;
import belejki.com.mvc.repository.UserReminderRepository;
import belejki.com.mvc.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;

@Repository
public class UserReminderRepositoryImpl implements UserReminderRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public UserReminderRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public List<UserReminderDto> findAllByUsername(String username, String token) throws RestClientException {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
	public List<UserReminderDto> findAll(String jwtToken) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

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
	public UserReminderDto save(UserReminderDto reminder, String jwtToken) throws RestClientException {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<UserReminderDto> request = new HttpEntity<>(reminder, headers);

		ResponseEntity<UserReminderDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/reminders",
				request,
				UserReminderDto.class);

		return response.getBody();
	}

	@Override
	public UserReminderDto edit(Long id, String jwtToken) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwtToken);

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
	public UserReminderDto update(Long id, UserReminderDto reminder, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);
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
	public UserReminderDto deleteById(Long id, String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
	public List<UserReminderDto> searchByNameContaining(String searchValue, String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(token);

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
