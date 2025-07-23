package belejki.com.mvc.user.registration.service;

import belejki.com.mvc.user.registration.web.binding.RegisterBindingModel;
import belejki.com.mvc.user.registration.web.dto.RegistrationDto;

public interface UserRegistrationService {
	RegistrationDto registerUser(RegisterBindingModel registerBindingModel);

}
