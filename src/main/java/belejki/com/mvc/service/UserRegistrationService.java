package belejki.com.mvc.service;

import belejki.com.mvc.model.binding.UserRegisterBindingModel;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface UserRegistrationService {
	String registerUser(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes, String captchaToken);

	String prepareRegistrationForm(Model model);
}
