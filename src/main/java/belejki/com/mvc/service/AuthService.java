package belejki.com.mvc.service;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

public interface AuthService {
	void prepareLoginPage(Locale locale, HttpSession session, Model model);

	void authenticate(UserLogingBindingModel userLogingBindingModel, Locale locale, HttpSession session, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult);
}
