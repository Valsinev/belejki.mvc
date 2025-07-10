package belejki.com.mvc.repository;

import belejki.com.mvc.dto.UserRegistrationDto;
import belejki.com.mvc.dto.UserSessionDto;

public interface UserRepository {

	UserSessionDto getCurrentUserInformation(String jwtToken);

	UserRegistrationDto save(UserRegistrationDto user);
}
