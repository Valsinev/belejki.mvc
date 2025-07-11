package belejki.com.mvc.service;


import belejki.com.mvc.model.dto.UserSessionDto;

public interface UserSessionService {

	void initUserSessionInfo(UserSessionDto userByUsername, String jwtToken);


}
