package belejki.com.mvc.model.dto;


import lombok.Data;

@Data
public class UserRegistrationDto {
	private String username;
	private String firstName;
	private String lastName;
	private String password;
	private String confirmPassword;
}
