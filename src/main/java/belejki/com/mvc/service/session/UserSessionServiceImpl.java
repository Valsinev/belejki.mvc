package belejki.com.mvc.service.session;

import belejki.com.mvc.model.dto.UserSessionDto;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSessionServiceImpl implements UserSessionService {

	private final UserSessionInformation userSessionInformation;

	@Autowired
	public UserSessionServiceImpl(UserSessionInformation userSessionInformation) {
		this.userSessionInformation = userSessionInformation;
	}

	@Override
	public void initUserSessionInfo(UserSessionDto dto, String jwtToken) {
		boolean isRootAdmin = dto.getAuthorities().contains(UserSessionRoles.ROLE_ROOT_ADMIN.toString());
		boolean isAdmin = dto.getAuthorities().contains(UserSessionRoles.ROLE_ADMIN.toString());

		userSessionInformation.setUserEmail(dto.getUsername());
		userSessionInformation.setFirstName(dto.getFirstName());
		userSessionInformation.setLastName(dto.getLastName());
		userSessionInformation.setId(dto.getId());
		userSessionInformation.setJwtToken(jwtToken);
		userSessionInformation.setRootAdmin(isRootAdmin);
		userSessionInformation.setAdmin(isAdmin);
	}
}
