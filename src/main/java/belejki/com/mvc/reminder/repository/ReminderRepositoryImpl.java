package belejki.com.mvc.reminder.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.reminder.web.dto.ReminderDto;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.shared.util.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Repository
public class ReminderRepositoryImpl implements ReminderRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;
	private final UserSessionInformation userSessionInformation;

	@Autowired
	public ReminderRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate, UserSessionInformation userSessionInformation) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.userSessionInformation = userSessionInformation;
	}

	@Override
	public List<ReminderDto> findAll() throws RestClientException {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
				}
		);

		return response.getBody().getContent();
	}

	@Override
	public ReminderDto save(ReminderDto reminder) throws RestClientException {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ReminderDto> request = new HttpEntity<>(reminder, headers);

		ResponseEntity<ReminderDto> response = restTemplate.postForEntity(
				appConfig.getBackendApiUrl() + "/user/reminders",
				request,
				ReminderDto.class);

		return response.getBody();
	}

	@Override
	public ReminderDto findById(Long id) throws RestClientException {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<ReminderDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/id/" + id,
				HttpMethod.GET,
				request,
				ReminderDto.class
		);

		return response.getBody();
	}

	@Override
	public ReminderDto update(ReminderDto reminder) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ReminderDto> request = new HttpEntity<>(reminder, headers);
		ResponseEntity<ReminderDto> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders",
				HttpMethod.PUT,
				request,
				ReminderDto.class
		);
		return response.getBody();
	}


	@Override
	public List<ReminderDto> findAllByNameContaining(String searchValue) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/name/" + searchValue,
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
				}
		);
		return response.getBody().getContent();
	}

	@Override
	public List<ReminderDto> findAllNotExpiredAndNotExpiresSoon() {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/not-soon",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
				}
		);

		return response.getBody().getContent();
	}

	@Override
	public List<ReminderDto> findAllExpiredReminders() {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/expired",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
				}
		);

		return response.getBody().getContent();
	}

	@Override
	public List<ReminderDto> findAllAlmostExpiredReminders() {


		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/expires-soon",
				HttpMethod.GET,
				request,
				new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
				}
		);

		return response.getBody().getContent();
	}

	@Override
	public void deleteById(Long id) {

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(userSessionInformation.getJwtToken());

		HttpEntity<Void> request = new HttpEntity<>(headers);

		ResponseEntity<Void> response = restTemplate.exchange(
				appConfig.getBackendApiUrl() + "/user/reminders/id/" + id,
				HttpMethod.DELETE,
				request,
				Void.class
		);
	}
}
