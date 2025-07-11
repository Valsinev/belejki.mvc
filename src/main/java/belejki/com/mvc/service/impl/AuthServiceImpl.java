package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.UserSessionDto;
import belejki.com.mvc.model.binding.UserLogingBindingModel;
import belejki.com.mvc.repository.AuthRepository;
import belejki.com.mvc.service.AuthService;
import belejki.com.mvc.service.UserService;
import belejki.com.mvc.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {

	private final AuthRepository authRepository;
	private final UserSessionService userSessionService;
	private final UserService userService;
	@Autowired
	public AuthServiceImpl(AuthRepository authRepository, UserSessionService userSessionService, UserService userService) {

		this.authRepository = authRepository;
		this.userSessionService = userSessionService;
		this.userService = userService;
	}

	@Override
	public String authenticate(UserLogingBindingModel userLogingBindingModel, Locale locale) {

		String jwtToken = authRepository.authUser(userLogingBindingModel, locale);

		UserSessionDto userByUsername = userService.findUserByUsername(jwtToken);

		//populates the SessionScope class UserSessionInformation
		userSessionService.initUserSessionInfo(userByUsername, jwtToken);

		return jwtToken;

	}
}
