package belejki.com.mvc.service;

import belejki.com.mvc.dto.RecipeDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Locale;

public interface UserRecipesService {
	void createRecipe(@Valid RecipeDto recipe, BindingResult bindingResult, HttpSession session);

	String getRecipes(HttpSession session);

	String searchByNameContaining(String searchValue, Model model, HttpSession session, Locale locale);

	String searchByIngredients(List<String> ingredients, Model model, HttpSession session, Locale locale);

	String deleteRecipeById(Long id, HttpSession session);
}
