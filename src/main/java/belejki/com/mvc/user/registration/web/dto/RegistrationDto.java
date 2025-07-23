package belejki.com.mvc.user.registration.web.dto;


import lombok.Data;

@Data
public class RegistrationDto {
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String confirmPassword;
}
