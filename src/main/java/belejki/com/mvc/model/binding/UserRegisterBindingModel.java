package belejki.com.mvc.model.binding;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterBindingModel {
    @Email(message = "{user.username.wrongEmailFormat}")
    @NotBlank(message = "{user.username.notBlank}")
    private String username;
    @Size(min = 2, max = 24, message = "{user.firstname.length}")
    private String firstName;
    @Size(min = 2, max = 24, message = "{user.lastname.length}")
    private String lastName;
    @Size(min = 8, max = 64, message = "{user.password.length}")
    private String password;
    @Size(min = 8, max = 64, message = "{user.confirmPassword.lengthOrMatch}")
    private String confirmPassword;
}
