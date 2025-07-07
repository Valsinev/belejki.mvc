package belejki.com.mvc.repository.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.repository.AuthRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Repository
public class AuthRepositoryImpl implements AuthRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public AuthRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public void authUser(UserLogingBindingModel userLogingBindingModel, Locale locale, HttpSession session, RedirectAttributes redirectAttributes) {
		//constructs the backend endpoint for post login requests
		String loginUrl = appConfig.getBackendApiUrl() + "/login";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("username", userLogingBindingModel.getUsername());
		requestBody.put("password", userLogingBindingModel.getPassword());
		requestBody.put("locale", locale.toLanguageTag());

		HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, request, Map.class);
		//gets the jwt token
		String token = (String) response.getBody().get("token");

		// Save token in session
		session.setAttribute("jwt", token);

	}
}
