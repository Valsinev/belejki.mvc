package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.UserInfoDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@Slf4j
public class HomeController {
//    @Value("${backend.api.url}")
//    private String backendApiUrl;

    private final RestTemplate restTemplate;
    private final AppConfig appConfig;

    @Autowired
    public HomeController(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    @GetMapping("/")
    public String getHomePage(Model theModel) {
        theModel.addAttribute("theYear", LocalDate.now().getYear());
        return "home";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userInfoDto", new UserInfoDto());
        return "fancy_registration";
    }


    @GetMapping("/registration/success")
    public String getSuccessRegistrationPage() {
        return "register_success";
    }

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false, defaultValue = "bg") String localeData, HttpSession session) {
        Locale locale = Locale.forLanguageTag(localeData);
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
        return "fancy_login";
    }

    @PostMapping("/login")
    public String getUserDashboard(@RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false, defaultValue = "bg") String localeData,
                                   HttpSession session,
                                   Model model) {
        Locale locale = Locale.forLanguageTag(localeData);
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);

        String loginUrl = appConfig.getBackendApiUrl() + "/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", username);
        requestBody.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(loginUrl, request, Map.class);
            String token = (String) response.getBody().get("token");


            // Save token in session
            session.setAttribute("jwt", token);

            String secret = "yourSuperSecretKeyForJWTSigning1234567890";
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
            List<String> roles = (List<String>) claims.get("roles");

            if (roles.contains("ROLE_ADMIN")) {
                return "redirect:/admin";
            } else {
                return "redirect:/user";
            }
        } catch (HttpClientErrorException ex) {
            model.addAttribute("error", "Invalid username or password.");
            return "fancy_login";
        }
    }

    @GetMapping("/login/error")
    public String getLoginPageWithErrorNotification(Model model) {
        model.addAttribute("message", "Invalid credentials.");
//        return "login";
        return "fancy_login";
    }

    @PostMapping("/registration")
    public String saveUser(
            @Valid @ModelAttribute("userInfoDto") UserInfoDto userInfoDto,
            BindingResult bindingResult,
            Model model,
            HttpServletResponse response) {

        log.info(userInfoDto.toString());

        if (bindingResult.hasErrors()) {
            log.error(bindingResult.getAllErrors().toString());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return "fancy_registration";
        }

        try {
            // Send to REST API
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserInfoDto> request = new HttpEntity<>(userInfoDto, headers);

            ResponseEntity<UserInfoDto> restResponse = restTemplate.postForEntity(
                    appConfig.getBackendApiUrl() + "/user/users", request, UserInfoDto.class);

            log.info("Saved user from REST API: " + restResponse.getBody());
            return "redirect:/registration/success";

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                // Parse error response
                try {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(e.getResponseBodyAsString());
                    String errorMessage = root.path("message").asText();

                    model.addAttribute("errorMessage", errorMessage); // Pass to template
                    response.setStatus(HttpStatus.BAD_REQUEST.value());
                    return "fancy_registration";

                } catch (IOException ex) {
                    log.error("Error parsing error response", ex);
                }
            }

            log.error("Unexpected error during registration", e);
            model.addAttribute("errorMessage", "Unexpected error occurred.");
            return "fancy_registration";
        }
    }


}
