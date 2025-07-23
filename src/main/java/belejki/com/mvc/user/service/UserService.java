package belejki.com.mvc.user.service;


import belejki.com.mvc.user.session.domain.UserSessionDto;

public interface UserService {
	UserSessionDto findUserByUsername(String jwtToken);
}
