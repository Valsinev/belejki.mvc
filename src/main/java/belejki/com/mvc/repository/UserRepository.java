package belejki.com.mvc.repository;

import belejki.com.mvc.dto.UserRegistrationDto;

public interface UserRepository {


	UserRegistrationDto save(UserRegistrationDto user);
}
