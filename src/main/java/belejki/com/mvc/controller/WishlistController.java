package belejki.com.mvc.controller;

import belejki.com.mvc.dto.WishDto;
import belejki.com.mvc.service.UserWishlistService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/wishes")
public class WishlistController {

    private final UserWishlistService userWishlistService;

    @Autowired
	public WishlistController(UserWishlistService userWishlistService) {
		this.userWishlistService = userWishlistService;
	}

	@GetMapping
    public String getUserWishlist(Model model, HttpSession session) {
        return userWishlistService.getWishlist(model, session);

    }

    @GetMapping("/create")
    public String GetWishForm(Model model) {
        model.addAttribute("wish", new WishDto());
        return "create_wish";
    }

    @PostMapping("/create")
    public String createWish(@Valid @ModelAttribute("wish") WishDto wish,
                             BindingResult result,
                             HttpSession session,
                             Model model) {
        return userWishlistService.createWish(wish, result, session, model);

    }

    @GetMapping("/edit/{id}")
    public String editWish(@PathVariable Long id,
                               HttpSession session,
                               Model model) {

        return userWishlistService.editWish(id, session, model);

    }

    @PostMapping("/update/{id}")
    public String updateWish(@PathVariable Long id,
                           @Valid @ModelAttribute("wish") WishDto wish,
                           BindingResult result,
                           HttpSession session,
                           Model model) {
        return userWishlistService.updateWish(id, wish, result, session, model);

    }



    @GetMapping("/delete/{id}")
    public String deleteWish(@PathVariable Long id, HttpSession session) {
        return userWishlistService.deleteWish(id, session);

    }

    @GetMapping("/search")
    public String searchByNameContaining(@RequestParam("searchValue") String searchValue,
                                         Model model,
                                         HttpSession session) {
        return userWishlistService.searchByNameContaining(searchValue, model, session);

    }

    @GetMapping("/filter")
    public String filterByPriceLessThan(@RequestParam("maxPrice") Long maxPrice,
                                         Model model,
                                         HttpSession session) {
        return userWishlistService.filterByPriceLessThan(maxPrice, model, session);

    }
}
