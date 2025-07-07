package belejki.com.mvc.controller;

import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import belejki.com.mvc.service.UserRegistrationService;
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

	@Autowired
	public UserRegistrationController(UserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}


	@GetMapping("/registration")
	public String showRegistrationForm(Model model) {
		return userRegistrationService.prepareRegistrationForm(model);
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

		return userRegistrationService.registerUser(userRegisterBindingModel, bindingResult, model, response, redirectAttributes, captchaToken);
	}
}
