package belejki.com.mvc.user.session.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Data
public class UserSessionInformation {

	private Long id;
	private String userEmail;
	private String firstName;
	private String lastName;
	private String jwtToken;
	private boolean isAdmin;
	private boolean isRootAdmin;

}
