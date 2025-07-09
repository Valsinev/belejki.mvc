package belejki.com.mvc.service.impl;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.repository.UserRecipesRepository;
import belejki.com.mvc.service.UserRecipesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecipesServiceImpl implements UserRecipesService {

	private final UserRecipesRepository userRecipesRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserRecipesServiceImpl(UserRecipesRepository userRecipesRepository, ModelMapper modelMapper) {
		this.userRecipesRepository = userRecipesRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public RecipeDto save(UserRecipeBindingModel userRecipeBindingModel, String jwtToken) {

		RecipeDto recipe = modelMapper.map(userRecipeBindingModel, RecipeDto.class);

		return userRecipesRepository.save(recipe, jwtToken);
	}

	@Override
	public List<RecipeDto> searchByNameContaining(String searchValue, String jwtToken) {

		return userRecipesRepository.findAllByNameContaining(searchValue, jwtToken);

	}

	@Override
	public List<RecipeDto> searchByIngredients(List<String> ingredients, String jwtToken) {

		return userRecipesRepository.findAllByIngredients(ingredients, jwtToken);

	}

	@Override
	public RecipeDto deleteRecipeById(Long id, String jwtToken) {

		return userRecipesRepository.deleteById(id, jwtToken);
	}
}
