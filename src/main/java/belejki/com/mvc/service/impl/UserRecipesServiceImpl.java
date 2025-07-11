package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.RecipeDto;
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
	public RecipeDto save(UserRecipeBindingModel userRecipeBindingModel) {

		RecipeDto recipe = modelMapper.map(userRecipeBindingModel, RecipeDto.class);

		return userRecipesRepository.save(recipe);
	}

	@Override
	public List<RecipeDto> searchByNameContaining(String searchValue) {

		return userRecipesRepository.findAllByNameContaining(searchValue);

	}

	@Override
	public List<RecipeDto> searchByIngredients(List<String> ingredients) {

		return userRecipesRepository.findAllByIngredients(ingredients);

	}

	@Override
	public RecipeDto deleteRecipeById(Long id) {

		return userRecipesRepository.deleteById(id);
	}
}
