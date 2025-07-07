package belejki.com.mvc.controller;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
public class AuthController {
	private final AuthService authService;

	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping("/login")
	public String getLoginPage(Locale locale, HttpSession session, Model model) {
		authService.prepareLoginPage(locale, session, model);
		return "fancy_login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("userLoginBindingModel") UserLogingBindingModel userLogingBindingModel,
	                    Locale locale,
	                    HttpSession session,
	                    Model model,
	                    RedirectAttributes redirectAttributes,
	                    BindingResult bindingResult) {

		//validates the login form
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("userLogingBindingModel", userLogingBindingModel);
			return "redirect:fancy_login";
		}
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		try {
			authService.authenticate(userLogingBindingModel, locale, session, model, redirectAttributes, bindingResult);
			return "redirect:/user";
		} catch (HttpClientErrorException ex) {
			redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
			redirectAttributes.addFlashAttribute("userLogingBindingModel", userLogingBindingModel);
			return "redirect:fancy_login";
		}
	}

	@GetMapping("/login/error")
	public String getLoginPageWithErrorNotification(Model model) {
		model.addAttribute("message", "Invalid credentials.");
		return "fancy_login";
	}
}
