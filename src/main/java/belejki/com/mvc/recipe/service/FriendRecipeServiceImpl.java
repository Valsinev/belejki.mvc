package belejki.com.mvc.recipe.service;

import belejki.com.mvc.recipe.web.dto.RecipeDto;
import belejki.com.mvc.recipe.web.view.RecipeViewModel;
import belejki.com.mvc.recipe.repository.FriendRecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRecipeServiceImpl implements FriendRecipeService {

	private final FriendRecipeRepository friendRecipeRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public FriendRecipeServiceImpl(FriendRecipeRepository friendRecipeRepository, ModelMapper modelMapper) {
		this.friendRecipeRepository = friendRecipeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<RecipeViewModel> getFriendRecipesByTitle(String searchValue, String username) {

		return friendRecipeRepository.getFriendRecipesByTitle(searchValue, username);

	}

	@Override
	public List<RecipeViewModel> getFriendRecipesByIngredients(List<String> ingredients, String username) {

		List<RecipeDto> friendRecipesByIngredients = friendRecipeRepository.getFriendRecipesByIngredients(ingredients, username);

		return friendRecipesByIngredients.stream()
				.map(recipeDto -> modelMapper.map(recipeDto, RecipeViewModel.class))
				.toList();

	}

}
