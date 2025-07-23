package belejki.com.mvc.user.login.service;

import belejki.com.mvc.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtServiceImpl implements JwtService {

	private final AppConfig appConfig;

	@Autowired
	public JwtServiceImpl(AppConfig appConfig) {
		this.appConfig = appConfig;
	}

	@Override
	public List<String> extractRoles(String token) {

		Claims claims = Jwts.parser()
				.setSigningKey(appConfig.getJwtSecret().getBytes())
				.parseClaimsJws(token)
				.getBody();
		return (List<String>) claims.get("roles");
	}

	@Override
	public String extractUsername(String token) {

			Claims claims = Jwts.parser()
					.setSigningKey(appConfig.getJwtSecret().getBytes())
					.parseClaimsJws(token)
					.getBody();
			return claims.getSubject();

	}
}
