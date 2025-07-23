package belejki.com.mvc.user.login.web;

import belejki.com.mvc.user.login.service.LoginService;
import belejki.com.mvc.user.login.web.binding.LoginBindingModel;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Locale;

@Controller
public class LoginController {
	private final LoginService loginService;
	private final MessageSource messageSource;

	@Autowired
	public LoginController(LoginService loginService, MessageSource messageSource) {
		this.loginService = loginService;
		this.messageSource = messageSource;
	}

	@GetMapping("/login")
	public String getLoginPage(Locale locale, HttpSession session, Model model) {
		model.addAttribute("theYear", LocalDate.now().getYear());

		if (!model.containsAttribute("loginBindingModel")) {
			model.addAttribute("loginBindingModel", new LoginBindingModel());
		}
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		return "login";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("loginBindingModel") LoginBindingModel loginBindingModel,
	                    BindingResult bindingResult,
	                    Locale locale,
	                    HttpSession session,
	                    RedirectAttributes redirectAttributes) {

		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		//validates the login form
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginBindingModel", bindingResult);
			redirectAttributes.addFlashAttribute("loginBindingModel", loginBindingModel);
			return "redirect:/login";
		}

		try {
			loginService.authenticate(loginBindingModel, locale);
			return "redirect:/home";
		} catch (HttpClientErrorException ex) {

			if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				String errorMsg = messageSource.getMessage("login.invalid.username.or.password", null, locale);
				redirectAttributes.addFlashAttribute("error", errorMsg);
				redirectAttributes.addFlashAttribute("loginBindingModel", loginBindingModel);
			}
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
