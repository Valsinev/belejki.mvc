package belejki.com.mvc.service;

import belejki.com.mvc.model.dto.UserRegistrationDto;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;

public interface UserRegistrationService {
	UserRegistrationDto registerUser(UserRegisterBindingModel userRegisterBindingModel);

}
