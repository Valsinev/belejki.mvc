package belejki.com.mvc.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

public interface UserFriendsService {
	String getFriends(Model model, HttpSession session);

	String prepareFriedRecipesPage(String username, Model model, HttpSession session);

	String getFriendRecipesByTitle(String searchValue, String username, Model model, HttpSession session, Locale locale);

	String getFriendRecipesByIngredients(List<String> ingredients, String username, Model model, HttpSession session, Locale locale);

	String getFriendWishlistFilteredByPrice(Long maxPrice, String username, Model model, HttpSession session);

	String prepareFriendWishlistPage(String username, Model model, HttpSession session);

	String addFriend(String friendEmail, Model model, HttpSession session);

	String removeFriend(Long id, HttpSession session);
}
