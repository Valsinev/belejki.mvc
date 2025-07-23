package belejki.com.mvc.recipe.web;

import belejki.com.mvc.shared.exceptions.UnauthorizedException;
import belejki.com.mvc.recipe.web.binding.RecipeBindingModel;
import belejki.com.mvc.recipe.web.view.RecipeViewModel;
import belejki.com.mvc.user.session.domain.UserSessionInformation;
import belejki.com.mvc.recipe.service.RecipeService;
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

    private final RecipeService recipeService;
    private final UserSessionInformation userInfo;

    @Autowired
	public RecipesController(RecipeService recipeService, UserSessionInformation userInfo) {
		this.recipeService = recipeService;
	    this.userInfo = userInfo;
    }


	@GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("recipeBindingModel")) {
            model.addAttribute("recipeBindingModel", new RecipeBindingModel());
        }
        return "create_recipe";
    }


    @PostMapping("/create")
    public String createNewRecipe(@Valid @ModelAttribute("recipeBindingModel") RecipeBindingModel recipeBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {

        if (userInfo.getJwtToken() == null) throw new UnauthorizedException("User authentication failed.");

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipeBindingModel", recipeBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipeBindingModel", bindingResult);
            return "redirect:/user/recipes/create";
        }
        try {
            recipeService.save(recipeBindingModel);
            return "redirect:/user/recipes";
        } catch (RestClientException e) {
            return "error";
        }

    }


    @GetMapping
    public String getUserRecipesPage() {

        if (userInfo.getJwtToken() == null) return "redirect:/user/dashboard";

        return "user_recipes";
    }



    @GetMapping("/search/title")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue, Model model) {

        if (userInfo.getJwtToken() == null) return "redirect:/user/dashboard";

        if (searchValue == null || searchValue.isBlank()) model.addAttribute("recipes", List.of());
        else {
            List<RecipeViewModel> recipes = recipeService.searchByNameContaining(searchValue);
            model.addAttribute("recipes", recipes);
        }

        return "user_recipes";
    }


    @GetMapping("/search/ingredients")
    public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients, Model model) {

        if (userInfo.getJwtToken() == null) return "redirect:/user/dashboard";

        List<RecipeViewModel> recipes = recipeService.searchByIngredients(ingredients);

        model.addAttribute("recipes", recipes);


        return "user_recipes";

    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {

        if (userInfo.getJwtToken() == null) return "redirect:/login";

        try {
            recipeService.deleteRecipeById(id);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            return "redirect:/user/reminders?error=delete";
        }
        return "redirect:/user/recipes";

    }






}


