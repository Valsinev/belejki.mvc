package belejki.com.mvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserInfoDto {
    @Email(message = "Invalid format for email address.")
    @NotBlank(message = "Username is required.")
    private String username;
    @Size(min = 2, max = 24, message = "First name must be between 2 and 24 characters.")
    private String firstName;
    @Size(min = 2, max = 24, message = "Last name must be between 2 and 24 characters.")
    private String lastName;
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters.")
    private String password;
}
