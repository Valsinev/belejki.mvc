package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
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

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/user/wishes")
public class WishlistController {

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;

    public WishlistController(AppConfig appConfig, RestTemplate restTemplate) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getUserWishlist(Model model, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

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
        wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

        model.addAttribute("wishlist", wishlist);

        return "user_wishlist";
    }

    @GetMapping("/create")
    public String GetWishForm(Model model) {
        model.addAttribute("wish", new WishDto());
        return "create_wish";
    }

    @PostMapping("/create")
    public String createWish(@Valid @ModelAttribute("wish") WishDto wish,
                             BindingResult result,
                             HttpSession session,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editing", false);
            return "create_wish";
        }

        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WishDto> request = new HttpEntity<>(wish, headers);

        restTemplate.postForEntity(
                appConfig.getBackendApiUrl() + "/user/wishlist",
                request,
                Void.class);

        return "redirect:/user/wishes";
    }

    @GetMapping("/edit/{id}")
    public String editReminder(@PathVariable Long id,
                               HttpSession session,
                               Model model) {

        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<WishDto> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/wishlist/" + id,
                HttpMethod.GET,
                request,
                WishDto.class
        );

        WishDto wish = response.getBody();

        model.addAttribute("wish", wish);
        model.addAttribute("editing", true);

        return "create_wish";
    }

    @PostMapping("/update/{id}")
    public String editWish(@PathVariable Long id,
                           @Valid @ModelAttribute("wish") WishDto wish,
                           BindingResult result,
                           HttpSession session,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("editing", true);
            return "create_wish";
        }

        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<WishDto> request = new HttpEntity<>(wish, headers);

        restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/wishlist/update/" + id,
                HttpMethod.PUT,
                request,
                Void.class
        );


        return "redirect:/user/wishes";
    }



    @GetMapping("/delete/{id}")
    public String deleteWish(@PathVariable Long id, HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/login";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {

            restTemplate.exchange(
                    appConfig.getBackendApiUrl() + "/user/wishlist/" + id,
                    HttpMethod.DELETE,
                    request,
                    Void.class
            );
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return "redirect:/user/wishes?error=delete";
        }
        return "redirect:/user/wishes";
    }

    @GetMapping("/search")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
                                         Model model,
                                         HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/wishlist/description/" + searchValue,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<WishDto>>() {
                }
        );
        List<WishDto> wishlist = response.getBody().getContent();
        wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

        model.addAttribute("wishlist", wishlist);


        return "user_wishlist";
    }

    @GetMapping("/filter")
    public String filterByPriceLessThan(@RequestParam("maxPrice") Long maxPrice,
                                         Model model,
                                         HttpSession session) {
        String token = (String) session.getAttribute("jwt");
        if (token == null) return "redirect:/user/dashboard";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<PagedResponse<WishDto>> response = restTemplate.exchange(
                appConfig.getBackendApiUrl() + "/user/wishlist/price/" + maxPrice,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<PagedResponse<WishDto>>() {
                }
        );
        List<WishDto> wishlist = response.getBody().getContent();
        wishlist.sort(Comparator.comparing(WishDto::getApproximatePrice).reversed());

        model.addAttribute("wishlist", wishlist);


        return "user_wishlist";
    }
}
