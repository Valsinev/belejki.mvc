package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.repository.UserRecipesRepository;
import belejki.com.mvc.service.UserRecipesService;
import belejki.com.mvc.util.PagedResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserRecipesServiceImpl implements UserRecipesService {

	private final UserRecipesRepository userRecipesRepository;

	public UserRecipesServiceImpl(UserRecipesRepository userRecipesRepository) {
		this.userRecipesRepository = userRecipesRepository;
	}

	@Override
	public void save(UserRecipeBindingModel recipe, String jwtToken) {

		userRecipesRepository.save(recipe, jwtToken);
	}

	@Override
	public List<UserRecipeBindingModel> searchByNameContaining(String searchValue, String jwtToken) {

		return userRecipesRepository.searchByNameContaining(searchValue, jwtToken);

	}

	@Override
	public List<UserRecipeBindingModel> searchByIngredients(List<String> ingredients, String jwtToken) {

		return userRecipesRepository.searchByIngredients(ingredients, jwtToken);

	}

	@Override
	public void deleteRecipeById(Long id, String jwtToken) {

		userRecipesRepository.deleteById(id, jwtToken);
	}
}
