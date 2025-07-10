package belejki.com.mvc.service;


import belejki.com.mvc.dto.UserSessionDto;

public interface UserService {
	UserSessionDto findUserByUsername(String jwtToken);
}
