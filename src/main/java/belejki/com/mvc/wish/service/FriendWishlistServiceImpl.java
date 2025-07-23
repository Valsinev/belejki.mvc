package belejki.com.mvc.wish.service;

import belejki.com.mvc.friendship.web.binding.FriendshipBindingModel;
import belejki.com.mvc.friendship.web.view.FriendshipViewModel;
import belejki.com.mvc.wish.repository.FriendWishRepository;
import belejki.com.mvc.wish.web.dto.WishDto;
import belejki.com.mvc.wish.web.view.WishViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendWishlistServiceImpl implements FriendWishlistService {

	private final FriendWishRepository friendWishRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public FriendWishlistServiceImpl(FriendWishRepository friendWishRepository, ModelMapper modelMapper) {
		this.friendWishRepository = friendWishRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<WishViewModel> getFriendWishlistFilteredByPrice(Long maxPrice, String friendUsername) {

		List<WishDto> wishlist = friendWishRepository.getFriendWishlistFilteredByPrice(maxPrice, friendUsername);

		return wishlist.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}

	@Override
	public List<WishViewModel> findFriendWishlist(String friendUsername) {

		List<WishDto> wishlist = friendWishRepository.getFriendWishlistByFriendUsername(friendUsername);

		return wishlist.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}
}
