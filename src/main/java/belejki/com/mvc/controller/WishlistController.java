package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.ReminderDto;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/user")
public class WishlistController {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    public WishlistController(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/wishlist")
    public String getUserReminders(Model model, HttpSession session) {String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/wishlist",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<WishDto>>() {
                }
        );
        List<WishDto> wishlist = response.getBody().getContent();

        model.addAttribute("wishlist", wishlist);

        return "user_wishlist";
    }
}
