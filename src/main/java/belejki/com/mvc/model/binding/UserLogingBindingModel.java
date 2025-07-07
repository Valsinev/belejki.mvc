package belejki.com.mvc.model.binding;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserLogingBindingModel {

	@Email
	@NotBlank(message = "{user.username.notBlank}")
	private String username;
	@Size(min = 8, max = 64, message = "{user.password.length}")
	private String password;
}
