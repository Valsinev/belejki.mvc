package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.WishDto;
import belejki.com.mvc.model.view.WishViewModel;
import belejki.com.mvc.repository.FriendWishRepository;
import belejki.com.mvc.service.FriendWishlistService;
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
	public List<WishViewModel> getFriendWishlistFilteredByPrice(Long maxPrice, String username) {

		List<WishDto> wishlist = friendWishRepository.getFriendWishlistFilteredByPrice(maxPrice, username);

		return wishlist.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}

	@Override
	public List<WishViewModel> prepareFriendWishlistPage(String username) {

		List<WishDto> wishlist = friendWishRepository.getFriendWishlistByFriendUsername(username);

		return wishlist.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}
}
