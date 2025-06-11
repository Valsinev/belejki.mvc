package belejki.com.mvc.config;

// AppProperties.java
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppConfig {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${backend.api.url}")
    private String backendApiUrl;

    @Value("${recaptcha.site}")
    private String recaptchaSiteKey;

    @Value("${recaptcha.secret}")
    private String recaptchaSecretKey;
}

