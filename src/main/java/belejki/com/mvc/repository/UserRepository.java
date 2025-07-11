package belejki.com.mvc.repository;

import belejki.com.mvc.model.dto.UserRegistrationDto;
import belejki.com.mvc.model.dto.UserSessionDto;

public interface UserRepository {

	UserSessionDto getCurrentUserInformation(String jwtToken);

	UserRegistrationDto save(UserRegistrationDto user);
}
