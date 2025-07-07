package belejki.com.mvc.service;

import belejki.com.mvc.dto.WishDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface UserWishlistService {
	String getWishlist(Model model, HttpSession session);

	String createWish(@Valid WishDto wish, BindingResult result, HttpSession session, Model model);

	String editWish(Long id, HttpSession session, Model model);

	String updateWish(Long id, @Valid WishDto wish, BindingResult result, HttpSession session, Model model);

	String deleteWish(Long id, HttpSession session);

	String searchByNameContaining(String searchValue, Model model, HttpSession session);

	String filterByPriceLessThan(Long maxPrice, Model model, HttpSession session);
}
