package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import belejki.com.mvc.service.UserRegistrationService;
import belejki.com.mvc.service.impl.RecaptchaValidationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserRegistrationController {

	private final UserRegistrationService userRegistrationService;
	private final AppConfig appConfig;
	private final RecaptchaValidationService recaptchaValidationService;

	@Autowired
	public UserRegistrationController(UserRegistrationService userRegistrationService, AppConfig appConfig, RecaptchaValidationService recaptchaValidationService) {
		this.userRegistrationService = userRegistrationService;
		this.appConfig = appConfig;
		this.recaptchaValidationService = recaptchaValidationService;
	}


	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {

		if (!model.containsAttribute("userRegisterBindingModel")) {
			model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
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
			@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
			BindingResult bindingResult,
			Model model,
			HttpServletResponse response,
			RedirectAttributes redirectAttributes,
			@RequestParam("g-recaptcha-response") String captchaToken) {
		//registration form validation
		if (bindingResult.hasErrors() ||
				!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
			redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
			return "redirect:/registration";
		}
		// 🔐 Validate reCAPTCHA
		if (!recaptchaValidationService.isCaptchaValid(captchaToken)) {
			redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
			redirectAttributes.addFlashAttribute("errorMessage", "Captcha validation failed. Please try again.");
			return "redirect:/registration";
		}

		userRegistrationService.registerUser(userRegisterBindingModel);

		return "register_success";
	}
}
