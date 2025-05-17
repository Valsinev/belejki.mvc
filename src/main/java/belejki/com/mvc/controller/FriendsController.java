package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.FriendshipDto;
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
@RequestMapping("/user/friends")
public class FriendsController {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    @Autowired
    public FriendsController(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }


    @GetMapping
    public String getfriends(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<FriendshipDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/friendships",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<FriendshipDto>>() {
                }
        );
        List<FriendshipDto> friends = response.getBody().getContent();

        model.addAttribute("friends", friends);

        return "user_friends";
    }
}
