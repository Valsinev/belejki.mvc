package belejki.com.mvc.user.login.service;

import belejki.com.mvc.user.login.web.binding.LoginBindingModel;
import belejki.com.mvc.user.login.repository.LoginRepository;
import belejki.com.mvc.user.session.domain.UserSessionDto;
import belejki.com.mvc.user.service.UserService;
import belejki.com.mvc.user.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LoginServiceImpl implements LoginService {

	private final LoginRepository loginRepository;
	private final UserSessionService userSessionService;
	private final UserService userService;
	@Autowired
	public LoginServiceImpl(LoginRepository loginRepository, UserSessionService userSessionService, UserService userService) {

		this.loginRepository = loginRepository;
		this.userSessionService = userSessionService;
		this.userService = userService;
	}

	@Override
	public String authenticate(LoginBindingModel loginBindingModel, Locale locale) {

		String jwtToken = loginRepository.authUser(loginBindingModel, locale);

		UserSessionDto userByUsername = userService.findUserByUsername(jwtToken);

		//populates the SessionScope class UserSessionInformation
		userSessionService.initUserSessionInfo(userByUsername, jwtToken);

		return jwtToken;

	}
}
