package belejki.com.mvc.controller;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.exceptions.UnauthorizedException;
import belejki.com.mvc.model.session.UserSessionInformation;
import belejki.com.mvc.service.UserRecipesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user/recipes")
public class RecipesController {

    private final UserRecipesService userRecipesService;
    private final UserSessionInformation userInfo;

    @Autowired
	public RecipesController(UserRecipesService userRecipesService, UserSessionInformation userInfo) {
		this.userRecipesService = userRecipesService;
	    this.userInfo = userInfo;
    }


	@GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("theYear", LocalDate.now().getYear());
        if (!model.containsAttribute("userRecipeBindingModel")) {
            model.addAttribute("userRecipeBindingModel", new UserRecipeBindingModel());
        }
        return "create_recipe";
    }


    @PostMapping("/create")
    public String createNewRecipe(@Valid @ModelAttribute("recipe") UserRecipeBindingModel userRecipeBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (userInfo.getJwtToken() == null) throw new UnauthorizedException("User authentication failed.");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRecipeBindingModel", userRecipeBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRecipeBindingModel", bindingResult);
            return "redirect:/user/recipes/create";
        }
        try {
            userRecipesService.save(userRecipeBindingModel);
            return "redirect:/user/recipes";
        } catch (RestClientException e) {
            return "redirect:/login";
        }

    }


    @GetMapping
    public String getUserRecipesPage(Model model) {
        model.addAttribute("theYear", LocalDate.now().getYear());

        if (userInfo.getJwtToken() == null) return "redirect:/user/dashboard";

        return "user_recipes";
    }



    @GetMapping("/search/title")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue, Model model) {

        if (userInfo.getJwtToken() == null) return "redirect:/user/dashboard";

        List<RecipeDto> recipes = userRecipesService.searchByNameContaining(searchValue);

        model.addAttribute("theYear", LocalDate.now().getYear());
        model.addAttribute("recipes", recipes);

        return "user_recipes";
    }


    @GetMapping("/search/ingredients")
    public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients, Model model) {

        if (userInfo.getJwtToken() == null) return "redirect:/user/dashboard";

        List<RecipeDto> recipes = userRecipesService.searchByIngredients(ingredients);

        model.addAttribute("theYear", LocalDate.now().getYear());
        model.addAttribute("recipes", recipes);


        return "user_recipes";

    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {

        if (userInfo.getJwtToken() == null) return "redirect:/login";

        try {
            userRecipesService.deleteRecipeById(id);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return "redirect:/user/reminders?error=delete";
        }
        return "redirect:/user/recipes";

    }


}


