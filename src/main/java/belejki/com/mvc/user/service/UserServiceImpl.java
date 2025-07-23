package belejki.com.mvc.user.service;

import belejki.com.mvc.user.session.domain.UserSessionDto;
import belejki.com.mvc.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserSessionDto findUserByUsername(String jwtToken) {
		return userRepository.getCurrentUserInformation(jwtToken);
	}
}
