package belejki.com.mvc.service;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import jakarta.servlet.http.HttpSession;

import java.util.Locale;

public interface AuthService {

	String authenticate(UserLogingBindingModel userLogingBindingModel, Locale locale);

	void logout();
}
