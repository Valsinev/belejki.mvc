package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.ReminderDto;
import belejki.com.mvc.dto.ShoppingItemDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/user")
public class ShoppingItemsController {

    private final RestTemplate restTemplate;
    private final AppConfig appConfig;

    @Autowired
    public ShoppingItemsController(RestTemplate restTemplate, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.appConfig = appConfig;
    }

    @GetMapping("/shoplist")
    public String getUserReminders(Model model, HttpSession session) {String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<ShoppingItemDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/shopping-list",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<ShoppingItemDto>>() {
                }
        );
        List<ShoppingItemDto> shoplist = response.getBody().getContent();

        model.addAttribute("shoplist", shoplist);

        return "user_shoplist";
    }
}
