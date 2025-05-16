package belejki.com.mvc.config;

// AppProperties.java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${backend.api.url}")
    private String backendApiUrl;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public String getBackendApiUrl() {
        return backendApiUrl;
    }
}

