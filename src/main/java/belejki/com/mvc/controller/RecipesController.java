package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.RecipeDto;
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
public class RecipesController {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    public RecipesController(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/recipes")
    public String getUserReminders(Model model, HttpSession session) {String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/recipes",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
                }
        );
        List<RecipeDto> recipes = response.getBody().getContent();

        model.addAttribute("recipes", recipes);

        return "user_recipes";
    }
}


