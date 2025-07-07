package belejki.com.mvc.controller;

import belejki.com.mvc.config.AppConfig;
import belejki.com.mvc.dto.FriendshipDto;
import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.dto.ShoppingItemDto;
import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.service.UserFriendsService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/user/friends")
public class FriendsController {

    private final UserFriendsService userFriendsService;

    @Autowired
    public FriendsController(UserFriendsService userFriendsService) {

	    this.userFriendsService = userFriendsService;
    }


    @GetMapping
    public String getUserFriends(Model model, HttpSession session) {
        return userFriendsService.getFriends(model, session);
    }

    @GetMapping("/recipes/{username}")
    public String getFriendRecipesPage(@PathVariable String username, Model model, HttpSession session) {
        return userFriendsService.prepareFriedRecipesPage(username, model, session);
    }

    @GetMapping("/recipes/search/title")
    public String searchByRecipeTitle(@RequestParam String searchValue,
                                      @RequestParam String username,
                                      Model model,
                                      HttpSession session,
                                      Locale locale) {

        return  userFriendsService.getFriendRecipesByTitle(searchValue, username, model, session, locale);
    }



    @GetMapping("/recipes/search/ingredients")
    public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients,
                                      @RequestParam("username") String username,
                                      Model model,
                                      HttpSession session,
                                      Locale locale) {

        return  userFriendsService.getFriendRecipesByIngredients(ingredients, username, model, session, locale);
    }

    @GetMapping("/wishlist/filter")
    public String filterByPrice(@RequestParam("maxPrice") Long maxPrice,
                                      @RequestParam("username") String username,
                                      Model model,
                                      HttpSession session) {
        return userFriendsService.getFriendWishlistFilteredByPrice(maxPrice, username, model, session);

    }



    @GetMapping("/wishlist/{username}")
    public String getFriendWishlistPage(@PathVariable String username, Model model, HttpSession session) {

        return userFriendsService.prepareFriendWishlistPage(username, model, session);

    }


    @PostMapping
    public String addFriend(@RequestParam("friend") String friendEmail, Model model, HttpSession session) {

        return userFriendsService.addFriend(friendEmail, model, session);
    }

    @GetMapping("/remove/{id}")
    public String removeFriend(@PathVariable Long id, HttpSession session) {
        return userFriendsService.removeFriend(id, session);

    }

}
