package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.ReminderDto;
import belejki.com.mvc.service.JwtService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class UsersController {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Autowired
    public UsersController(AppConfig appConfig, RestTemplate restTemplate, JwtService jwtService) {
	    this.appConfig = appConfig;
	    this.restTemplate = restTemplate;
	    this.jwtService = jwtService;
    }

    @GetMapping("/user")
    public String getUserDashboard(HttpSession session, Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<ReminderDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/reminders",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<ReminderDto>>() {
                }
        );
        List<ReminderDto> reminders = response.getBody().getContent();
        List<ReminderDto> expired = reminders.stream().filter(ReminderDto::isExpired).toList();
        List<ReminderDto> expiresSoon = reminders.stream().filter(ReminderDto::isExpiresSoon).toList();


        model.addAttribute("user", jwtService.extractUsername(token));
        model.addAttribute("remindersCount", reminders.size());
        model.addAttribute("expiredCount", expired.size());
        model.addAttribute("almost", expiresSoon.size());
        model.addAttribute("expiredReminders", expired);
        model.addAttribute("almostExpiredReminders", expiresSoon);
        return "user";
    }



}
