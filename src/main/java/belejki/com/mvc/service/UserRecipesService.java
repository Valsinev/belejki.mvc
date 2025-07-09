package belejki.com.mvc.service;

import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

public interface UserRecipesService {
	void save(@Valid UserRecipeBindingModel recipe, String jwtToken);

	List<UserRecipeBindingModel> searchByNameContaining(String searchValue, String jwtToken);

	List<UserRecipeBindingModel> searchByIngredients(List<String> ingredients, String jwtToken);

	void deleteRecipeById(Long id, String jwtToken);
}
