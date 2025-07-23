package belejki.com.mvc.user.login.service;

import belejki.com.mvc.user.login.web.binding.LoginBindingModel;

import java.util.Locale;

public interface LoginService {

	String authenticate(LoginBindingModel loginBindingModel, Locale locale);

}
