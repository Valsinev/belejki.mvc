package belejki.com.mvc.service.impl;

import belejki.com.mvc.model.dto.WishDto;
import belejki.com.mvc.model.binding.UserWishBindingModel;
import belejki.com.mvc.model.view.WishViewModel;
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
	public List<WishViewModel> getWishlist() {

		List<WishDto> all = userWishRepository.findAll();

		return all.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}

	@Override
	public WishViewModel createWish(UserWishBindingModel userWishBindingModel) {

		WishDto wish = modelMapper.map(userWishBindingModel, WishDto.class);

		WishDto saved = userWishRepository.save(wish);

		return modelMapper.map(saved, WishViewModel.class);
	}

	@Override
	public WishViewModel editWish(Long id) {

		WishDto edited = userWishRepository.edit(id);

		return modelMapper.map(edited, WishViewModel.class);
	}

	@Override
	public WishViewModel updateWish(Long id, UserWishBindingModel userWishBindingModel) {

		WishDto wish = modelMapper.map(userWishBindingModel, WishDto.class);

		WishDto updated = userWishRepository.update(id, wish);

		return modelMapper.map(updated, WishViewModel.class);
	}

	@Override
	public WishViewModel deleteWish(Long id) {

		WishDto deleted = userWishRepository.deleteById(id);

		return modelMapper.map(deleted, WishViewModel.class);
	}

	@Override
	public List<WishViewModel> searchByNameContaining(String searchValue) {

		List<WishDto> allByNameContaining = userWishRepository.findAllByNameContaining(searchValue);

		return allByNameContaining.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}

	@Override
	public List<WishViewModel> filterByPriceLessThan(Long maxPrice) {

		List<WishDto> allByPriceLessThan = userWishRepository.findAllByPriceLessThan(maxPrice);

		return allByPriceLessThan.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}
}
