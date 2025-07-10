package belejki.com.mvc.controller;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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

import java.time.LocalDate;
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
		model.addAttribute("theYear", LocalDate.now().getYear());

		if (!model.containsAttribute("userLogingBindingModel")) {
			model.addAttribute("userLogingBindingModel", new UserLogingBindingModel());
		}
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		return "login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("userLogingBindingModel") UserLogingBindingModel userLogingBindingModel,
	                    BindingResult bindingResult,
	                    Locale locale,
	                    HttpSession session,
	                    RedirectAttributes redirectAttributes) {

		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		//validates the login form
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLogingBindingModel", bindingResult);
			redirectAttributes.addFlashAttribute("userLogingBindingModel", userLogingBindingModel);
			return "redirect:/login";
		}

		try {
			authService.authenticate(userLogingBindingModel, locale);
			return "redirect:/home";
		} catch (HttpClientErrorException ex) {
			redirectAttributes.addFlashAttribute("error", "{login.invalid.username.or.password}");
			redirectAttributes.addFlashAttribute("userLogingBindingModel", userLogingBindingModel);
			return "redirect:login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/login/error")
	public String getLoginPageWithErrorNotification(Model model) {
		model.addAttribute("message", "{invalid.credentials}");
		return "login";
	}
}
