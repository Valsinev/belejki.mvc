package belejki.com.mvc.user.repository;

import belejki.com.mvc.user.registration.web.dto.RegistrationDto;
import belejki.com.mvc.user.session.domain.UserSessionDto;

public interface UserRepository {

	UserSessionDto getCurrentUserInformation(String jwtToken);

	RegistrationDto save(RegistrationDto user);
}
