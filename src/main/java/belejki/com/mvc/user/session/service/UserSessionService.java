package belejki.com.mvc.user.session.service;


import belejki.com.mvc.user.session.domain.UserSessionDto;

public interface UserSessionService {

	void initUserSessionInfo(UserSessionDto userByUsername, String jwtToken);


}
