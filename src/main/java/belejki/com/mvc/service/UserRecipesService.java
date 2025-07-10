package belejki.com.mvc.service;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

public interface UserRecipesService {
	RecipeDto save(UserRecipeBindingModel recipe);

	List<RecipeDto> searchByNameContaining(String searchValue);

	List<RecipeDto> searchByIngredients(List<String> ingredients);

	RecipeDto deleteRecipeById(Long id);
}
