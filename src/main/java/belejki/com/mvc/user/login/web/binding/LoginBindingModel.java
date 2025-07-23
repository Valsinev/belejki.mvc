package belejki.com.mvc.user.login.web.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginBindingModel {

	@Email
	@NotBlank(message = "{user.username.notBlank}")
	private String username;
	@Size(min = 8, max = 64, message = "{user.password.length}")
	private String password;
}
