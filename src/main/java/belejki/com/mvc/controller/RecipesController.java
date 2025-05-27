package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user/recipes")
public class RecipesController {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;
    private final MessageSource messageSource;

    public RecipesController(AppConfig appConfig, RestTemplate restTemplate, MessageSource messageSource) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
        this.messageSource = messageSource;
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

        if (result.hasErrors()) {
            return "create_recipe";
        }
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
    public String getUserRecipes(Model model, HttpSession session, Locale locale) {String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        return "user_recipes";
    }



    @GetMapping("/search/title")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
                                         Model model,
                                         HttpSession session,
                                         Locale locale) {
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

        checkForVideoLink(locale, recipes);

        model.addAttribute("recipes", recipes);


        return "user_recipes";
    }


    @GetMapping("/search/ingredients")
    public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients,
                                         Model model,
                                         HttpSession session,
                                      Locale locale) {
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

        checkForVideoLink(locale, recipes);

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


    private void checkForVideoLink(Locale locale, List<RecipeDto> recipes) {
        String videoPrefix = messageSource.getMessage("video.prefix", null, locale);
        String linkText = messageSource.getMessage("video.watch.here", null, locale);

        String regex = "(?i)[\\r\\n\\s]*" + Pattern.quote(videoPrefix) + ":\\s*(https?://\\S+)";
        String replacement = "<strong>" + videoPrefix + ":</strong> <a href=\"$1\" target=\"_blank\">" + linkText + "</a>";

        Pattern pattern = Pattern.compile(regex);

        recipes.forEach(recipeDto -> {
            Matcher matcher = pattern.matcher(recipeDto.getHowToMake());
            String result = matcher.replaceAll(replacement);
            recipeDto.setHowToMake(result);
        });
    }
}


