package belejki.com.mvc.controller;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.exceptions.UnauthorizedException;
import belejki.com.mvc.service.UserRecipesService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/user/recipes")
public class RecipesController {

    private final UserRecipesService userRecipesService;

    @Autowired
	public RecipesController(UserRecipesService userRecipesService) {
		this.userRecipesService = userRecipesService;
	}


	@GetMapping("/create")
    public String showCreateForm(Model model) {
        if (!model.containsAttribute("recipe")) {
            model.addAttribute("recipe", new RecipeDto());
        }
        return "create_recipe";
    }


    @PostMapping("/create")
    public String createNewRecipe(@Valid @ModelAttribute("recipe") RecipeDto recipe,
                                  BindingResult bindingResult,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("recipe", recipe);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipe", bindingResult);
            return "redirect:/user/recipes/create";
        }
        try {
            userRecipesService.createRecipe(recipe, bindingResult, session);
            return "redirect:/user/recipes";
        } catch (UnauthorizedException e) {
            return "redirect:/login";
        }

    }


    @GetMapping
    public String getUserRecipes(HttpSession session) {
        return userRecipesService.getRecipes(session);
    }



    @GetMapping("/search/title")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
                                         Model model,
                                         HttpSession session,
                                         Locale locale) {

        return userRecipesService.searchByNameContaining(searchValue, model, session, locale);
    }


    @GetMapping("/search/ingredients")
    public String searchByIngredients(@RequestParam("ingredients") List<String> ingredients,
                                         Model model,
                                         HttpSession session,
                                      Locale locale) {
        return userRecipesService.searchByIngredients(ingredients, model, session, locale);

    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id, HttpSession session) {
        return userRecipesService.deleteRecipeById(id, session);
    }



}


