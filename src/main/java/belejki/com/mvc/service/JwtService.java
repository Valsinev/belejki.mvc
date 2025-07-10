package belejki.com.mvc.service;

import java.util.List;

public interface JwtService {
	List<String> extractRoles(String token);

	String extractUsername(String token);
}
