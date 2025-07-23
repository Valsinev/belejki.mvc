package belejki.com.mvc.wish.service;

import belejki.com.mvc.wish.web.binding.WishBindingModel;
import belejki.com.mvc.wish.web.dto.WishDto;
import belejki.com.mvc.wish.repository.WishRepository;
import belejki.com.mvc.wish.web.view.WishViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishServiceImpl implements WishService {

	private final WishRepository wishRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public WishServiceImpl(WishRepository wishRepository, ModelMapper modelMapper) {
		this.wishRepository = wishRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<WishViewModel> getWishlist() {

		List<WishDto> all = wishRepository.findAll();

		return all.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}

	@Override
	public WishViewModel createWish(WishBindingModel wishBindingModel) {

		WishDto wish = modelMapper.map(wishBindingModel, WishDto.class);

		WishDto saved = wishRepository.save(wish);

		return modelMapper.map(saved, WishViewModel.class);
	}

	@Override
	public WishViewModel editWish(Long id) {

		WishDto edited = wishRepository.edit(id);

		return modelMapper.map(edited, WishViewModel.class);
	}

	@Override
	public WishViewModel updateWish(WishBindingModel wishBindingModel) {

		WishDto wish = modelMapper.map(wishBindingModel, WishDto.class);

		WishDto updated = wishRepository.update(wish);

		return modelMapper.map(updated, WishViewModel.class);
	}

	@Override
	public void deleteWish(Long id) {

		wishRepository.deleteById(id);

	}

	@Override
	public List<WishViewModel> searchByNameContaining(String searchValue) {

		List<WishDto> allByNameContaining = wishRepository.findAllByNameContaining(searchValue);

		return allByNameContaining.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}

	@Override
	public List<WishViewModel> filterByPriceLessThan(Long maxPrice) {

		List<WishDto> allByPriceLessThan = wishRepository.findAllByPriceLessThan(maxPrice);

		return allByPriceLessThan.stream()
				.map(wishDto -> modelMapper.map(wishDto, WishViewModel.class))
				.toList();
	}

	@Override
	public WishViewModel findById(Long id) {

		WishDto byId = wishRepository.findById(id);

		return modelMapper.map(byId, WishViewModel.class);
	}
}
