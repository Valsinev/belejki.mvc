package belejki.com.mvc.service.session;

import belejki.com.mvc.model.session.UserSessionInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSessionService {

	private final UserSessionInformation userSession;

	@Autowired
	public UserSessionService(UserSessionInformation userSession) {
		this.userSession = userSession;
	}

	public void initializeSession(String email, String firstName, String lastName, String role, Long id, boolean isAdmin) {
		userSession.setId(id);
		userSession.setUserEmail(email);
		userSession.setFirstName(firstName);
		userSession.setLastName(lastName);
		userSession.setRole(role);
		userSession.setAdmin(isAdmin);
	}

	public void logout() {
		userSession.setId(null);
		userSession.setRole(null);
		userSession.setUserEmail(null);
	}
}
