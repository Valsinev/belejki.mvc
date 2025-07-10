package belejki.com.mvc.service;

import belejki.com.mvc.dto.UserRegistrationDto;
import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserRegistrationService {
	UserRegistrationDto registerUser(UserRegisterBindingModel userRegisterBindingModel);

}
