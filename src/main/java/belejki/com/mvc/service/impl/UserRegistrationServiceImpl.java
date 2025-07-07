package belejki.com.mvc.service.impl;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import belejki.com.mvc.service.CaptchaService;
import belejki.com.mvc.service.UserRegistrationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Log4j2
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	private final AppConfig appConfig;
	private final CaptchaService captchaService;
	private final RestTemplate restTemplate;

	@Autowired
	public UserRegistrationServiceImpl(AppConfig appConfig, CaptchaService captchaService, RestTemplate restTemplate) {
		this.appConfig = appConfig;
		this.captchaService = captchaService;
		this.restTemplate = restTemplate;
	}

	@Override
	public String prepareRegistrationForm(Model model) {
		if (!model.containsAttribute("userRegisterBindingModel")) {
			model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
		}
		model.addAttribute("recaptchaSiteKey", appConfig.getRecaptchaSiteKey());
		return "fancy_registration";
	}

	@Override
	public String registerUser(UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes, String captchaToken) {

		//registration form validation
		if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
			redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
			return "redirect:/registration";
		}

		// 🔐 Validate reCAPTCHA
		if (!captchaService.isCaptchaValid(captchaToken)) {
			model.addAttribute("errorMessage", "Captcha validation failed. Please try again.");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return "fancy_registration";
		}

		try {
			// Send to REST API
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<UserRegisterBindingModel> request = new HttpEntity<>(userRegisterBindingModel, headers);


			ResponseEntity<UserRegisterBindingModel> restResponse = restTemplate.postForEntity(
					appConfig.getBackendApiUrl() + "/user/users", request, UserRegisterBindingModel.class);

			log.info("Saved user from REST API: " + restResponse.getBody());
			return "redirect:/registration/success";

		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
				// Parse error response
				try {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(e.getResponseBodyAsString());
					String errorMessage = root.path("message").asText();

					model.addAttribute("errorMessage", errorMessage); // Pass to template
					response.setStatus(HttpStatus.BAD_REQUEST.value());
					return "fancy_registration";

				} catch (IOException ex) {
					log.error("Error parsing error response", ex);
				}
			}

			log.error("Unexpected error during registration", e);
			model.addAttribute("errorMessage", "Unexpected error occurred.");
			return "fancy_registration";
		}
	}
}
