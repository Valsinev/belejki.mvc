package belejki.com.mvc.recipe.service;

import belejki.com.mvc.recipe.web.binding.RecipeBindingModel;
import belejki.com.mvc.recipe.web.dto.RecipeDto;
import belejki.com.mvc.recipe.web.view.RecipeViewModel;
import belejki.com.mvc.recipe.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, ModelMapper modelMapper) {
		this.recipeRepository = recipeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public RecipeViewModel save(RecipeBindingModel recipeBindingModel) {

		RecipeDto recipe = modelMapper.map(recipeBindingModel, RecipeDto.class);

		RecipeDto saved = recipeRepository.save(recipe);

		return modelMapper.map(saved, RecipeViewModel.class);
	}

	@Override
	public List<RecipeViewModel> searchByNameContaining(String searchValue) {

		List<RecipeDto> allByNameContaining = recipeRepository.findAllByNameContaining(searchValue);

		return allByNameContaining.stream()
				.map(recipeDto -> modelMapper.map(recipeDto, RecipeViewModel.class))
				.toList();

	}

	@Override
	public List<RecipeViewModel> searchByIngredients(List<String> ingredients) {

		List<RecipeDto> allByIngredients = recipeRepository.findAllByIngredients(ingredients);

		return allByIngredients.stream()
				.map(recipeDto -> modelMapper.map(recipeDto, RecipeViewModel.class))
				.toList();

	}
	@Override
	public void deleteRecipeById(Long id) {

		recipeRepository.deleteById(id);
	}
}
