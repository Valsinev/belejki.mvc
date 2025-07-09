package belejki.com.mvc.controller;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.service.AuthService;
import belejki.com.mvc.service.session.UserSessionService;
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
	public String login(@Valid @ModelAttribute("userLoginBindingModel") UserLogingBindingModel userLogingBindingModel,
	                    Locale locale,
	                    HttpSession session,
	                    RedirectAttributes redirectAttributes,
	                    BindingResult bindingResult) {

		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		//validates the login form
		if (bindingResult.hasErrors()) {
			// Preserve validation errors across redirect
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userLogingBindingModel", bindingResult);
			redirectAttributes.addFlashAttribute("userLogingBindingModel", userLogingBindingModel);
			return "redirect:/login";
		}
		try {
			String jwtToken = authService.authenticate(userLogingBindingModel, locale);
			// Save token in session
			session.setAttribute("jwt", jwtToken);
			return "redirect:/home";
		} catch (HttpClientErrorException ex) {
			redirectAttributes.addFlashAttribute("error", "Invalid username or password.");
			redirectAttributes.addFlashAttribute("userLogingBindingModel", userLogingBindingModel);
			return "redirect:login";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		authService.logout();
		return "index";
	}

	@GetMapping("/login/error")
	public String getLoginPageWithErrorNotification(Model model) {
		model.addAttribute("message", "Invalid credentials.");
		return "login";
	}
}
