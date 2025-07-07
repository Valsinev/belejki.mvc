package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.repository.AuthRepository;
import belejki.com.mvc.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthRepository authRepository;
	@Autowired
	public AuthServiceImpl(AuthRepository authRepository) {

		this.authRepository = authRepository;
	}

	@Override
	public void prepareLoginPage(Locale locale, HttpSession session, Model model) {
		if (!model.containsAttribute("userLogingBindingModel")) {
			model.addAttribute("userLogingBindingModel", new UserLogingBindingModel());
		}
		session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
	}

	@Override
	public void authenticate(UserLogingBindingModel userLogingBindingModel,
	                           Locale locale,
	                           HttpSession session,
	                           Model model,
	                           RedirectAttributes redirectAttributes,
	                           BindingResult bindingResult) {


		authRepository.authUser(userLogingBindingModel, locale, session, redirectAttributes);

	}
}
