package belejki.com.mvc.controller;

import belejki.com.mvc.dto.ReminderDto;
import belejki.com.mvc.util.PagedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class UsersController {
    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${backend.api.url}")
    private String backendApiUrl;

    private final RestTemplate restTemplate;

    @Autowired
    public UsersController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/user")
    public String getUserDashboard(HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
                backendApiUrl + "/user/reminders",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
                }
        );
        List<ReminderDto> reminders = response.getBody().getContent();
        List<ReminderDto> expired = reminders.stream().filter(ReminderDto::isExpired).toList();
        List<ReminderDto> expiresSoon = reminders.stream().filter(ReminderDto::isExpiresSoon).toList();


        model.addAttribute("user", extractUsername(token));
        model.addAttribute("remindersCount", reminders.size());
        model.addAttribute("expiredCount", expired.size());
        model.addAttribute("expiredReminders", expired);
        model.addAttribute("almost", expiresSoon.size());
        model.addAttribute("almostExpiredReminders", expiresSoon);
        return "user";
    }


    private String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
