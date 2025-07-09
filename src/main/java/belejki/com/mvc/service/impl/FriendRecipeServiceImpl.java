package belejki.com.mvc.service.impl;

import belejki.com.mvc.dto.RecipeDto;
import belejki.com.mvc.model.binding.UserRecipeBindingModel;
import belejki.com.mvc.repository.FriendRecipeRepository;
import belejki.com.mvc.service.FriendRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendRecipeServiceImpl implements FriendRecipeService {

	private final FriendRecipeRepository friendRecipeRepository;

	@Autowired
	public FriendRecipeServiceImpl(FriendRecipeRepository friendRecipeRepository) {
		this.friendRecipeRepository = friendRecipeRepository;
	}

	@Override
	public List<RecipeDto> getFriendRecipesByTitle(String searchValue, String username, String jwtToken) {

		return friendRecipeRepository.getFriendRecipesByTitle(searchValue, username, jwtToken);

	}

	@Override
	public List<RecipeDto> getFriendRecipesByIngredients(List<String> ingredients, String username, String jwtToken) {

		return friendRecipeRepository.getFriendRecipesByIngredients(ingredients, username, jwtToken);

	}

}
