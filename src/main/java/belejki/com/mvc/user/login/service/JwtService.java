package belejki.com.mvc.user.login.service;

import java.util.List;

public interface JwtService {
	List<String> extractRoles(String token);

	String extractUsername(String token);
}
