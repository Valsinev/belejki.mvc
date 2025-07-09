package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.repository.AuthRepository;
import belejki.com.mvc.service.AuthService;
import belejki.com.mvc.service.session.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthRepository authRepository;
	private final UserSessionService userSessionService;
	@Autowired
	public AuthServiceImpl(AuthRepository authRepository, UserSessionService userSessionService) {

		this.authRepository = authRepository;
		this.userSessionService = userSessionService;
	}

	@Override
	public String authenticate(UserLogingBindingModel userLogingBindingModel, Locale locale) {

		String jwtToken = authRepository.authUser(userLogingBindingModel, locale);


		//TODO: initialize UserSessionInformation class
		/*
			1.fetch the user from userRepository
			2.check if isAdmin == true
			3.set role = "admin"
			4.mapp to UserSessionInformation with userSessionService.initializeSession(...)
		 */

		return jwtToken;

	}

	@Override
	public void logout() {
		userSessionService.logout();
	}
}
