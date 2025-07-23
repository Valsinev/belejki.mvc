package belejki.com.mvc.user.login.repository;

import belejki.com.mvc.user.login.web.binding.LoginBindingModel;

import java.util.Locale;

public interface LoginRepository {
	String authUser(LoginBindingModel loginBindingModel, Locale locale);
}
