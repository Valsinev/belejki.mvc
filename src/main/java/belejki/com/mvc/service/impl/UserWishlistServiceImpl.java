package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;
import belejki.com.mvc.repository.UserWishRepository;
import belejki.com.mvc.service.UserWishlistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWishlistServiceImpl implements UserWishlistService {

	private final UserWishRepository userWishRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public UserWishlistServiceImpl(UserWishRepository userWishRepository, ModelMapper modelMapper) {
		this.userWishRepository = userWishRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<WishDto> getWishlist() {

		return userWishRepository.findAll();
	}

	@Override
	public WishDto createWish(UserWishBindingModel userWishBindingModel) {

		WishDto wish = modelMapper.map(userWishBindingModel, WishDto.class);

		return userWishRepository.save(wish);

	}

	@Override
	public WishDto editWish(Long id) {

		return userWishRepository.edit(id);

	}

	@Override
	public WishDto updateWish(Long id, UserWishBindingModel userWishBindingModel) {

		WishDto wish = modelMapper.map(userWishBindingModel, WishDto.class);

		return userWishRepository.update(id, wish);

	}

	@Override
	public WishDto deleteWish(Long id) {

		return userWishRepository.deleteById(id);

	}

	@Override
	public List<WishDto> searchByNameContaining(String searchValue) {

		if (searchValue.isEmpty()) {
			return List.of();
		}
		return userWishRepository.findAllByNameContaining(searchValue);

	}

	@Override
	public List<WishDto> filterByPriceLessThan(Long maxPrice) {

		return userWishRepository.findAllByPriceLessThan(maxPrice);

	}
}
