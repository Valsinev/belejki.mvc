package belejki.com.mvc.service;


import belejki.com.mvc.dto.UserSessionDto;

public interface UserSessionService {

	void initUserSessionInfo(UserSessionDto userByUsername, String jwtToken);


}
