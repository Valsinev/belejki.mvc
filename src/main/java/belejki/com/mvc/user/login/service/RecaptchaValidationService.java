package belejki.com.mvc.user.login.service;

import belejki.com.mvc.config.AppConfig;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Log4j2
@Service
public class RecaptchaValidationService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final AppConfig appConfig;

    @Autowired
    public RecaptchaValidationService(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public boolean isCaptchaValid(String token) {
        String url = "https://www.google.com/recaptcha/api/siteverify";

        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
        requestMap.add("secret", appConfig.getRecaptchaSecretKey());
        requestMap.add("response", token);

        try {
            RecaptchaResponse response = restTemplate.postForObject(url, requestMap, RecaptchaResponse.class);
            log.info("reCAPTCHA response: {}", response);
            return response != null && response.isSuccess();
        } catch (Exception e) {
            log.error("Captcha validation failed", e);
            return false;
        }
    }

    @Data
    private static class RecaptchaResponse {
        private boolean success;
        private List<String> errorCodes;
    }
}
