package belejki.com.mvc.user.registration.web;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.user.login.service.RecaptchaValidationService;
import belejki.com.mvc.user.registration.web.binding.RegisterBindingModel;
import belejki.com.mvc.user.registration.service.UserRegistrationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class UserRegistrationController {

	private final UserRegistrationService userRegistrationService;
	private final AppConfig appConfig;
	private final RecaptchaValidationService recaptchaValidationService;
	private final MessageSource messageSource;

	@Autowired
	public UserRegistrationController(UserRegistrationService userRegistrationService, AppConfig appConfig, RecaptchaValidationService recaptchaValidationService, MessageSource messageSource) {
		this.userRegistrationService = userRegistrationService;
		this.appConfig = appConfig;
		this.recaptchaValidationService = recaptchaValidationService;
		this.messageSource = messageSource;
	}


	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {

		if (!model.containsAttribute("registerBindingModel")) {
			model.addAttribute("registerBindingModel", new RegisterBindingModel());
		}
		model.addAttribute("recaptchaSiteKey", appConfig.getRecaptchaSiteKey());
		return "fancy_registration";
	}


	@GetMapping("/registration/success")
	public String getSuccessRegistrationPage() {
		return "register_success";
	}


	@PostMapping("/registration")
	public String saveUser(
			@Valid @ModelAttribute("registerBindingModel") RegisterBindingModel registerBindingModel,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			@RequestParam("g-recaptcha-response") String captchaToken,
			Locale locale) throws JsonProcessingException {
		//registration form validation
		if (bindingResult.hasErrors() ||
				!registerBindingModel.getPassword().equals(registerBindingModel.getConfirmPassword())) {
			redirectAttributes.addFlashAttribute("registerBindingModel", registerBindingModel);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerBindingModel", bindingResult);
			return "redirect:/registration";
		}
		// 🔐 Validate reCAPTCHA
		if (!recaptchaValidationService.isCaptchaValid(captchaToken)) {
			String errorMsg = messageSource.getMessage("user.registration.captcha.validation.failed", null, locale);

			redirectAttributes.addFlashAttribute("registerBindingModel", registerBindingModel);
			redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
			return "redirect:/registration";
		}

		try {
			userRegistrationService.registerUser(registerBindingModel);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.CONFLICT) {
				String errorMsg = messageSource.getMessage("user.registration.username.already.taken", null, locale);
				redirectAttributes.addFlashAttribute("errorMessage", errorMsg);
				return "redirect:/registration";
			}
		}
		return "register_success";
	}
}
