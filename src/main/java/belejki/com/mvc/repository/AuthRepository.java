package belejki.com.mvc.repository;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

public interface AuthRepository {
	void authUser(UserLogingBindingModel userLogingBindingModel, Locale locale, HttpSession session, RedirectAttributes redirectAttributes);
}
