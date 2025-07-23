package belejki.com.mvc.user.login.repository;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.user.login.web.binding.LoginBindingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Repository
public class LoginRepositoryImpl implements LoginRepository {
	private final AppConfig appConfig;
	private final RestTemplate restTemplate;

	@Autowired
	public LoginRepositoryImpl(AppConfig appConfig, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
	}

	@Override
	public String authUser(LoginBindingModel loginBindingModel, Locale locale) {
		//constructs the backend endpoint for post login requests
		String loginUrl = appConfig.getBackendApiUrl() + "/login";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> requestBody = new HashMap<>();
		requestBody.put("username", loginBindingModel.getUsername());
		requestBody.put("password", loginBindingModel.getPassword());
		requestBody.put("locale", locale.toLanguageTag());

		HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

		ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, request, Map.class);
		//gets the jwt token
		return  (String) response.getBody().get("token");

	}
}
