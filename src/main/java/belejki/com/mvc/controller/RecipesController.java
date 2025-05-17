package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.dto.ReminderDto;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/user/recipes")
public class RecipesController {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    public RecipesController(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        return "create_recipe";
    }


    @PostMapping("/create")
    public String createNewRecipe(@Valid @ModelAttribute("recipe") RecipeDto recipe,
                                    BindingResult result,
                                    HttpSession session,
                                    Model model) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RecipeDto> request = new HttpEntity<>(recipe, headers);

        restTemplate.postForEntity(
                appConfig.getBackendApiUrl() + "/user/recipes",
                request,
                Void.class);

        return "redirect:/user/recipes";
    }


    @GetMapping
    public String getUserRecipes(Model model, HttpSession session) {String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);


        return "user_recipes";
    }



    @GetMapping("/search/title")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
                                         Model model,
                                         HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/recipes/" + searchValue,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
                }
        );
        List<RecipeDto> recipes = response.getBody().getContent();

        model.addAttribute("recipes", recipes);


        return "user_recipes";
    }


    @GetMapping("/search/ingredients")
    public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients,
                                         Model model,
                                         HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromHttpUrl(appConfig.getBackendApiUrl() + "/user/recipes/by-ingredients")
                .queryParam("ingredients", ingredients.toArray())
                .build()
                .encode()
                .toUri();

        ResponseEntity<PagedResponse<RecipeDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<RecipeDto>>() {
                }
        );
        List<RecipeDto> recipes = response.getBody().getContent();

        model.addAttribute("recipes", recipes);


        return "user_recipes";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id,
                                 HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(
                    appConfig.getBackendApiUrl() + "/user/recipes/" + id,
                    HttpMethod.DELETE,
                    request,
                    Void.class
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return "redirect:/user/reminders?error=delete";
        }
        return "redirect:/user/recipes";
    }
}


