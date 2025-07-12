package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.model.view.RecipeIngredientViewModel;
import belejki.com.mvc.model.view.RecipeViewModel;
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
	public RecipeViewModel save(UserRecipeBindingModel userRecipeBindingModel) {

		RecipeDto recipe = modelMapper.map(userRecipeBindingModel, RecipeDto.class);

		RecipeDto saved = userRecipesRepository.save(recipe);

		return modelMapper.map(saved, RecipeViewModel.class);
	}

	@Override
	public List<RecipeViewModel> searchByNameContaining(String searchValue) {

		List<RecipeDto> allByNameContaining = userRecipesRepository.findAllByNameContaining(searchValue);

		return allByNameContaining.stream()
				.map(recipeDto -> modelMapper.map(recipeDto, RecipeViewModel.class))
				.toList();

	}

	@Override
	public List<RecipeViewModel> searchByIngredients(List<String> ingredients) {

		List<RecipeDto> allByIngredients = userRecipesRepository.findAllByIngredients(ingredients);

		return allByIngredients.stream()
				.map(recipeDto -> modelMapper.map(recipeDto, RecipeViewModel.class))
				.toList();

	}

	@Override
	public RecipeViewModel deleteRecipeById(Long id) {

		RecipeDto deleted = userRecipesRepository.deleteById(id);

		return modelMapper.map(deleted, RecipeViewModel.class);
	}
}
