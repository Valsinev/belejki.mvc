package belejki.com.mvc.repository;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

public interface AuthRepository {
	String authUser(UserLogingBindingModel userLogingBindingModel, Locale locale);
}
