package belejki.com.mvc.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSessionDto {

	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private List<String> authorities;
	private boolean isAdmin;
}
